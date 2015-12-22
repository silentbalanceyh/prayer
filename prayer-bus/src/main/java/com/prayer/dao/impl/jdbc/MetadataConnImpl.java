package com.prayer.dao.impl.jdbc;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.jvmError;
import static com.prayer.util.cv.Accessors.pool;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractDbPool;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.Metadata;
import com.prayer.util.cv.Constants;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 *
 * @author Lang
 * @see
 */
@Guarded
public class MetadataConnImpl implements MetadataConn {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataConnImpl.class);
    // ~ Instance Fields =====================================
    /**
     * 获取的数据库访问连接池
     */
    @NotNull
    private transient final AbstractDbPool dbPool = singleton(pool());
    /**
     * 数据库H2的访问连接池
     */
    @NotNull
    private transient final AbstractDbPool h2Pool;
    /**
     * 获取当前数据库的元数据
     */
    @NotNull
    private transient Metadata metadata;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 默认构造函数
     */
    @PostValidateThis
    public MetadataConnImpl() {
        // 元数据连接池初始化
        try (final Connection conn = dbPool.getJdbc().getDataSource().getConnection()) {
            final DatabaseMetaData sqlMeta = conn.getMetaData();
            /**
             * 因为metadata和runner在try块中，所以不能设置成final修饰，否则编译会无法通过
             */
            metadata = new Metadata(sqlMeta, dbPool.getCategory());
            conn.close();
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
        }
        // H2 元数据
        h2Pool = new BoneCPPool("H2");
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 获取元数据信息
     */
    @Override
    @NotNull
    public Metadata getMetadata() {
        return this.metadata;
    }

    /**
     * 加载SQL文件
     */
    @Override
    @Pre(expr = "_this.dbPool != null", lang = Constants.LANG_GROOVY)
    public boolean loadSqlFile(@NotNull final InputStream sqlFile) {
        boolean ret = false;
        try (final Connection conn = this.dbPool.getJdbc().getDataSource().getConnection()) {
            this.runScript(conn, sqlFile);
            // 默认日志级别输出SQL语句是DEBUG级别，只要不是级别则不会输出
            conn.close();
            ret = true;
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
            ret = false;
        }
        return ret;
    }

    /**
     * 初始化元数据
     */
    @Override
    @Pre(expr = "_this.h2Pool != null", lang = Constants.LANG_GROOVY)
    public boolean initMeta(@NotNull final InputStream sqlFile) {
        boolean ret = false;
        try (final Connection conn = this.h2Pool.getJdbc().getDataSource().getConnection()) {
            this.runScript(conn, sqlFile);
            conn.close();
            ret = true;
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
            ret = false;
        }
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void runScript(final Connection conn, final InputStream sqlFile) throws SQLException {
        final ScriptRunner runner = new ScriptRunner(conn);
        final Reader sqlReader = new InputStreamReader(sqlFile);
        runner.setSendFullScript(true);
        runner.runScript(sqlReader);
        runner.closeConnection();
    }
    // ~ hashCode,equals,toString ============================
}
