package com.prayer.base.builder;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.constant.log.DebugKey;
import com.prayer.dao.impl.builder.SqlDDLBuilder;
import com.prayer.facade.dao.builder.Builder;
import com.prayer.facade.dao.builder.SQLStatement;
import com.prayer.facade.kernel.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.model.meta.database.PEField;
import com.prayer.pool.impl.jdbc.RecordConnImpl;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 抽象层的Builder信息
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractBuilder implements Builder, SQLStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    /** Builder **/
    private transient final SqlDDLBuilder builder;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public AbstractBuilder() {
        this.connection = singleton(RecordConnImpl.class);
        this.builder = SqlDDLBuilder.create();
    }

    // ~ Abstract Methods ====================================
    /** 获取Referencer信息 **/
    public abstract Referencer getReferencer();

    /** 判断当前表是否存在 **/
    public abstract DataValidator getValidator();

    /** 日志记录器 **/
    public abstract Logger getLogger();

    /** 自增长字段语句 **/
    public abstract String getAutoIncrement(PEField primaryKey);

    // ~ Override Methods =====================================
    /** **/
    @Override
    public boolean synchronize(@NotNull final Schema schema) throws AbstractDatabaseException {
        final boolean exist = this.exist(schema);
        // 表是否存在，不存在则执行Create，存在则执行Alter
        String sql = null;
        if (exist) {
            sql = this.prepAlterSql(schema);
        } else {
            sql = this.prepCreateSql(schema);
        }
        debug(getLogger(), DebugKey.INFO_SQL_STMT, sql);
        final int respCode = this.connection.executeBatch(sql);
        return Constants.RC_SUCCESS == respCode;
    }

    /**
     * 删除表语句
     **/
    @Override
    public boolean purge(@NotNull final Schema schema) throws AbstractDatabaseException {
        final boolean exist = this.exist(schema);
        if (exist) {
            final String sql = builder.buildDropTable(schema.getTable());
            debug(getLogger(), DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.connection.executeBatch(sql);
            return Constants.RC_SUCCESS == respCode;
        } else {
            return false;
        }
    }

    // ~ Methods =============================================
    /** 子类使用连接 **/
    protected JdbcConnection getConnection() {
        return this.connection;
    }
    // ~ Private Methods =====================================

    private boolean exist(final Schema schema) {
        final String table = schema.getTable();
        /** 验证表是否存在 **/
        AbstractSchemaException error = this.getValidator().verifyTable(table);
        /** 如果error为空则表不存在 **/
        return !(null == error);
    }

    private String prepAlterSql(final Schema schema) {
        final List<String> lines = new ArrayList<>();

        return StringKit.join(lines, Symbol.SEMICOLON);
    }

    /**
     * 创建表语句
     * 
     * @param schema
     * @return
     */
    private String prepCreateSql(final Schema schema) {
        final List<String> lines = new ArrayList<>();
        /** 1.自增长定义行 **/

        return builder.buildCreateTable(schema.getTable(), lines);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
