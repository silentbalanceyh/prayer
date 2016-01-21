package com.prayer.fantasm.pool;

import static com.prayer.constant.Accessors.pool;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.reservoir;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcOperations;

import com.prayer.constant.Constants;
import com.prayer.constant.MemoryPool;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.pool.JdbcPool;
import com.prayer.model.business.Metadata;
import com.prayer.model.type.DataType;
import com.prayer.util.jdbc.Input;
import com.prayer.util.jdbc.Output;
import com.prayer.util.jdbc.SqlKit;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractJdbcConnection implements JdbcConnection {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @InstanceOfAny(AbstractJdbcPool.class)
    private transient final JdbcPool dbPool; // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractJdbcConnection(@NotEmpty @NotBlank final String category) {
        synchronized (getClass()) {
            if (null == category) {
                // Fix数据源切换的问题
                this.dbPool = reservoir(MemoryPool.POOL_CONPOOL, "DEFAULT", pool());
            } else {
                this.dbPool = reservoir(MemoryPool.POOL_CONPOOL, category, pool(), category);
            }
        }
    }

    // ~ Abstract Methods ====================================
    /** 子类处理方法 **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /** **/
    @Override
    public int execute(@NotNull @NotBlank @NotEmpty final String sql, final List<Value<?>> params) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        int ret = Constants.RC_FAILURE;
        if (null == params) {
            jdbc.execute(sql);
            // 直接执行只要不抛异常，不计算行数，返回0
            ret = Constants.RC_SUCCESS;
        } else {
            ret = jdbc.execute(Input.prepStmt(sql, params), Output.extractExecuteResult());
            // 在不抛异常的情况下，如果ret >= 0则表示执行成功，返回影响行数，否则返回false
            ret = Constants.ZERO <= ret ? ret : Constants.RC_FAILURE;
        }
        return ret;
    }

    /** **/
    @Override
    public Long count(@NotNull @NotBlank @NotEmpty final String sql) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        return jdbc.queryForObject(sql, Long.class);
    }

    /** **/
    @Override
    public List<ConcurrentMap<String, Value<?>>> select(@NotNull @NotBlank @NotEmpty final String sql,
            final List<Value<?>> params, @MinSize(1) final ConcurrentMap<String, DataType> columnMap,
            @MinSize(0) final String... columns) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        if (null == params) {
            return jdbc.query(sql, Output.extractDataList(columnMap, columns));
        } else {
            return jdbc.query(Input.prepStmt(sql, params), Output.extractDataList(columnMap, columns));
        }
    }

    /** **/
    @Override
    public List<String> select(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @NotBlank @NotEmpty final String column) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        return jdbc.query(sql, Output.extractColumnList(column));
    }

    /** **/
    @Override
    public List<ConcurrentMap<String, String>> select(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @MinSize(2) final String[] columns) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        return jdbc.query(sql, Output.extractColumnList(columns));
    }

    /** **/
    @Override
    public Value<?> insert(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @MinSize(1) final List<Value<?>> values, final boolean isRetKey, final DataType retType) {
        final JdbcOperations jdbc = this.getPool().getExecutor();
        return jdbc.execute(Input.prepStmt(sql, values, isRetKey), Output.extractIncrement(isRetKey, retType));
    }

    /** for oracle **/
    @Override
    public int executeBatch(@NotNull @NotBlank @NotEmpty final String sql) {
        return SqlKit.execute(this.getPool().getDataSource(), sql);
    }

    // ~ Methods =============================================
    /** **/
    @Override
    public boolean executeSql(InputStream sqlFile) {
        final Reader reader = new InputStreamReader(sqlFile);
        return Constants.RC_SUCCESS == SqlKit.execute(this.getPool().getDataSource(), reader);
    }

    /**
     * 根据数据库类型读取数据库的Metadata
     * 
     * @param category
     * @return
     */
    @Override
    public Metadata getMetadata(String category) {
        Metadata ret = null;
        try (final Connection conn = this.getPool().getDataSource().getConnection()) {
            final DatabaseMetaData sqlMeta = conn.getMetaData();
            ret = new Metadata(sqlMeta, this.getPool().getCategory());
        } catch (SQLException ex) {
            jvmError(getLogger(), ex);
        }
        return ret;
    }

    /** 获取Jdbc连接池引用 **/
    private JdbcPool getPool() {
        return this.dbPool;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
