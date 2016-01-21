package com.prayer.builder.impl;

import java.text.MessageFormat;
import java.util.Locale;

import com.prayer.base.builder.AbstractValidator;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.facade.builder.special.MsSqlStatement;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class MsSqlValidator extends AbstractValidator implements MsSqlStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 检查表是否存在的SQL语句 **/
    @Override
    protected String buildTableSQL(@NotNull @NotEmpty @NotBlank final String table) {
        return MessageFormat.format(E_TABLE, Resources.DB_DATABASE, table);
    }

    /** 检查表中列是否存在的SQL语句 **/
    @Override
    protected String buildColumnSQL(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        return MessageFormat.format(E_COLUMN, Resources.DB_DATABASE, table, column);
    }

    /** 检查表中列对应的外键约束是否匹配 **/
    @Override
    protected String buildConstraintSQL(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        return MessageFormat.format(E_FK_CHECK, Resources.DB_DATABASE, table, column);
    }

    /** 检查表中对应列的类型是否匹配 **/
    @Override
    protected String buildTypeSQL(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column, @NotNull @NotEmpty @NotBlank final String type) {
        return MessageFormat.format(E_FK_CHECK_TYPE, Resources.DB_DATABASE, table, column, type);
    }

    /** 类型查看器 **/
    @Override
    protected String typeFilter(@NotNull @NotEmpty @NotBlank final String expectedType) {
        // 参数处理
        String type = Constants.EMPTY_STR;
        if (Constants.RANGE < expectedType.indexOf('(')) {
            type = expectedType.split("(")[Constants.IDX];
            if (StringKit.isNonNil(type)) {
                type = type.toLowerCase(Locale.getDefault());
            }
        } else {
            type = expectedType;
        }
        return type;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
