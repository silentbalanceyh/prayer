package com.prayer.dao.impl.builder;

import static com.prayer.util.Instance.reservoir;

import java.util.Locale;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.exception.schema.BFKConstraintInvalidException;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.exception.schema.BTableNotExistingException;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.schema.DataValidator;
import com.prayer.util.StringKit;
import com.prayer.util.cv.MemoryPool;
import com.prayer.util.cv.Resources;

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
public final class MsSqlValidator implements DataValidator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcContext.class)
    private transient final JdbcContext context; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 
     */
    @PostValidateThis
    public MsSqlValidator() {
        this.context = reservoir(MemoryPool.POOL_JDBC, Resources.DB_CATEGORY, JdbcConnImpl.class);
    }

    /**
     * 检查表是否存在的情况
     */
    @Override
    public AbstractSchemaException verifyTable(@NotNull @NotEmpty @NotBlank final String table) {
        final String sql = MsSqlHelper.getSqlTableExist(table);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTableNotExistingException(getClass(), table);
        }
        return error;
    }

    /**
     * 检查字段是否存在的情况
     */
    @Override
    public AbstractSchemaException verifyColumn(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = MsSqlHelper.getSqlColumnExist(table, column);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTColumnNotExistingException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段是否Unique或者Primary Key <code>Sub Table必须满足</code>
     * <code>Foreign Key关联的Table也必须满足</code>
     */
    @Override
    public AbstractSchemaException verifyConstraint(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = MsSqlHelper.getSqlUKPKConstraint(table, column);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BFKConstraintInvalidException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段类型是否和当前类型一致
     */
    @Override
    public AbstractSchemaException verifyColumnType(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column, @NotNull @NotEmpty @NotBlank final String expectedType) {
        // 处理参数
        String type = "";
        if (0 < expectedType.indexOf('(')) {
            type = expectedType.split("(")[0];
            if (StringKit.isNonNil(type)) {
                type = type.toLowerCase(Locale.getDefault());
            }
        }else{
            type = expectedType;
        }
        final String sql = MsSqlHelper.getSqlColumnType(table, column, type);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTColumnTypeInvalidException(getClass(), table, column, type);
        }
        return error;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
