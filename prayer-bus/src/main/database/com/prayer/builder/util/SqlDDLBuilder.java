package com.prayer.builder.util;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.List;

import com.prayer.constant.Symbol;
import com.prayer.facade.builder.SQLStatement;
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
public final class SqlDDLBuilder implements SQLStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 创建Builder **/
    public static SqlDDLBuilder create() {
        return singleton(SqlDDLBuilder.class);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 构建表创建语句
     * 
     * @param table
     * @param lines
     * @return
     */
    public String buildCreateTable(@NotNull @NotBlank @NotEmpty final String table, @NotNull final List<String> lines) {
        return MessageFormat.format(TB_CREATE, table, StringKit.join(lines, Symbol.COMMA));
    }

    /**
     * 构建表删除语句
     * 
     * @param table
     * @return
     */
    public String buildDropTable(@NotNull @NotBlank @NotEmpty final String table) {
        return MessageFormat.format(TB_DROP, table);
    }

    /**
     * 删除表的列信息
     * 
     * @param table
     * @param column
     * @return
     */
    public String buildDropColumn(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String column) {
        return MessageFormat.format(ATBD_COLUMN, table, column);
    }

    /**
     * 更新表的列信息
     * 
     * @param table
     * @param colLine
     * @return
     */
    public String buildAlterColumn(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String colLine) {
        return MessageFormat.format(ATBM_COLUMN, table, colLine);
    }

    /**
     * 添加表的列信息
     * 
     * @param table
     * @param colLine
     * @return
     */
    public String buildAddColumn(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String colLine) {
        return MessageFormat.format(ATBA_COLUMN, table, colLine);
    }

    /**
     * 添加表的约束信息
     * 
     * @param table
     * @param csLine
     * @return
     */
    public String buildAddConstraint(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String csLine) {
        return MessageFormat.format(ATBA_CONSTRAINT, table, csLine);
    }

    /**
     * 修改表中的约束
     * 
     * @param table
     * @param constraint
     * @return
     */
    public String buildDropConstraint(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String constraint) {
        return MessageFormat.format(ATBD_CONSTRAINT, table, constraint);
    }

    /**
     * 检查表中指定的列是否有空数据
     * 
     * @param table
     * @param column
     * @return
     */
    public String buildNullSQL(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String column) {
        return MessageFormat.format(SCHEMA_NULL, table, column);
    }

    /**
     * 检查表中某个字段是否有重复数据
     * 
     * @param table
     * @param column
     * @return
     */
    public String buildUniqueSQL(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull @NotBlank @NotEmpty final String column) {
        return MessageFormat.format(SCHEMA_UNIQUE, table, column);
    }

    // ~ Private Methods =====================================
    private SqlDDLBuilder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
