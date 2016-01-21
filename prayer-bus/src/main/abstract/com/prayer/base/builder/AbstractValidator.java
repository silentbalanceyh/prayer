package com.prayer.base.builder;

import static com.prayer.util.reflection.Instance.reservoir;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.exception.schema.BKeyConstraintInvalidException;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.exception.schema.BTableNotExistingException;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.pool.impl.jdbc.RecordConnImpl;

import net.sf.oval.constraint.InstanceOf;
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
public abstract class AbstractValidator implements DataValidator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public AbstractValidator() {
        this.connection = reservoir(MemoryPool.POOL_JDBC, Resources.DB_CATEGORY, RecordConnImpl.class);
    }

    // ~ Abstract Methods ====================================
    /** 检查数据库中的Table是否存在 **/
    protected abstract String buildTableSQL(final String table);

    /** 检查数据库中的Table对应Name是否存在 **/
    protected abstract String buildColumnSQL(final String table, final String column);

    /** 检查数据库中的Table对应的约束是否匹配 **/
    protected abstract String buildConstraintSQL(final String table, final String column);

    /** 检查数据库中的Table对应列类型是否匹配 **/
    protected abstract String buildTypeSQL(final String table, final String column, final String type);

    /** 参数过滤器 **/
    protected abstract String typeFilter(final String expectedType);

    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /** 检查表是否存在 **/
    public AbstractSchemaException verifyTable(@NotNull @NotEmpty @NotBlank final String table) {
        final String sql = this.buildTableSQL(table);
        final Long counter = this.connection.count(sql);
        AbstractSchemaException error = null;
        if (counter <= Constants.NO_ROW) {
            error = new BTableNotExistingException(getClass(), table);
        }
        return error;
    }

    /** 检查表中对应的列是否存在 **/
    public AbstractSchemaException verifyColumn(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = this.buildColumnSQL(table, column);
        final Long counter = this.connection.count(sql);
        AbstractSchemaException error = null;
        if (counter <= Constants.NO_ROW) {
            error = new BTColumnNotExistingException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段是否Unique或者Primary Key <code>Sub Table必须满足</code>
     * <code>Foreign Key关联的Table也必须满足</code>
     */
    public AbstractSchemaException verifyConstraint(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = this.buildConstraintSQL(table, column);
        final Long counter = this.connection.count(sql);
        AbstractSchemaException error = null;
        if (counter <= Constants.NO_ROW) {
            error = new BKeyConstraintInvalidException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段类型是否和当前类型一致
     */
    public AbstractSchemaException verifyColumnType(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column, @NotNull @NotEmpty @NotBlank final String expectedType) {
        final String type = this.typeFilter(expectedType);
        final String sql = this.buildTypeSQL(table, column, type);
        final Long counter = this.connection.count(sql);
        AbstractSchemaException error = null;
        if (counter <= Constants.NO_ROW) {
            error = new BTColumnTypeInvalidException(getClass(), table, column, type);
        }
        return error;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
