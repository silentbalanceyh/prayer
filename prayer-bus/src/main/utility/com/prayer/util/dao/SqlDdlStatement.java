package com.prayer.util.dao;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.model.database.FieldModel;
import com.prayer.model.database.KeyModel;
import com.prayer.util.StringKit;
import com.prayer.util.io.PropertyKit;

import jodd.util.ArraysUtil;
import jodd.util.StringUtil;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Sql语句工具类，主要提供各种Builder使用的辅助工具类
 * 
 * @author Lang
 *
 */
@Guarded
public final class SqlDdlStatement implements SqlSegment, Symbol {
    // ~ Static Fields =======================================
    /** 数据库类型映射 **/
    public static final ConcurrentMap<String, String> DB_TYPES = new ConcurrentHashMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 初始化数据类型映射表，直接根据Database填充 **/
    static {
        final PropertyKit loader = new PropertyKit(SqlDdlStatement.class, Resources.DB_TYPES_FILE);
        final Properties prop = loader.getProp();
        for (final Object key : prop.keySet()) {
            if (null != key && StringKit.isNonNil(key.toString())) {
                final String keyStr = key.toString();
                final String[] keys = keyStr.split("\\.");
                if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.DB_CATEGORY)
                        && StringKit.isNonNil(keys[1])) {
                    DB_TYPES.put(keys[1], prop.getProperty(keyStr));
                }
            }
        }
    }

    // ~ Static Methods ======================================
    /**
     * 生成外键约束的SQL语句： CONSTRAINT FK_NAME FOREIGN KEY (COLUMN) REFERENCES
     * REF_TABLE(REF_ID)
     * 
     * @param foreignKey
     * @param foreignField
     * @return
     */
    @NotNull
    public static String newFKSql(@NotNull @NotBlank @NotEmpty final String fKeyName,
            @NotNull @MinSize(1) final List<String> columns, @NotNull @NotBlank @NotEmpty final String toTable,
            @NotNull @NotBlank @NotEmpty final String toColumn) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.添加外键约束
        sql.append(CONSTRAINT).append(SPACE).append(fKeyName).append(SPACE).append(FOREIGN).append(SPACE).append(KEY)
                .append(SPACE).append(BRACKET_SL).append(StringKit.join(columns, COMMA)).append(BRACKET_SR)
                .append(SPACE).append(REFERENCES).append(SPACE).append(toTable).append(BRACKET_SL).append(toColumn)
                .append(BRACKET_SR).append(SPACE);
        return sql.toString();
    }

    /**
     * 生成外键约束的SQL语句： CONSTRAINT FK_NAME FOREIGN KEY (COLUMN) REFERENCES
     * REF_TABLE(REF_ID)
     * 
     * @param foreignKey
     * @param foreignField
     * @return
     */
    @NotNull
    public static String newFKSql(@InstanceOfAny(KeyModel.class) final KeyModel foreignKey,
            @InstanceOfAny(FieldModel.class) final FieldModel foreignField) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.添加外键约束
        if (null != foreignKey && null != foreignField && KeyCategory.ForeignKey == foreignKey.getCategory()
                && foreignField.isForeignKey()) {
            sql.append(newFKSql(foreignKey.getName(), foreignKey.getColumns(), foreignField.getRefTable(),
                    foreignField.getRefId()));
            // sql.append(CONSTRAINT).append(SPACE).append(foreignKey.getName()).append(SPACE).append(FOREIGN)
            // .append(SPACE).append(KEY).append(SPACE).append(BRACKET_SL)
            // .append(StringKit.join(foreignKey.getColumns(),
            // COMMA)).append(BRACKET_SR).append(SPACE)
            // .append(REFERENCES).append(SPACE).append(foreignField.getRefTable()).append(BRACKET_SL)
            // .append(foreignField.getRefId()).append(BRACKET_SR).append(SPACE);
        }
        return sql.toString();
    }

    /**
     * 生成Unique键约束的SQL语句： CONSTRAINT UK_NAME UNIQUE (COL1,COL2)
     * 
     * @param key
     * @return
     */
    @NotNull
    public static String newUKSql(@NotNull @InstanceOfAny(KeyModel.class) final KeyModel key) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.添加Unique键约束
        if (KeyCategory.UniqueKey == key.getCategory()) {
            sql.append(CONSTRAINT).append(SPACE).append(key.getName()).append(SPACE).append(UNIQUE).append(SPACE)
                    .append(BRACKET_SL).append(StringKit.join(key.getColumns(), COMMA)).append(BRACKET_SR);
        }
        return sql.toString();
    }

    /**
     * 生成Primary Key主键约束的SQL语句：CONSTRAINT PK_NAME PRIMARY KEY (COL1,COL2)
     * 
     * @param key
     * @return
     */
    @NotNull
    public static String newPKSql(@NotNull @InstanceOfAny(KeyModel.class) final KeyModel key) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.添加主键约束
        if (KeyCategory.PrimaryKey == key.getCategory()) {
            sql.append(CONSTRAINT).append(SPACE).append(key.getName()).append(SPACE).append(PRIMARY).append(SPACE)
                    .append(KEY).append(SPACE).append(BRACKET_SL).append(StringKit.join(key.getColumns(), COMMA))
                    .append(BRACKET_SR);
        }
        return sql.toString();
    }

    /**
     * 生成列定义的SQL语句：NAME VARCHAR(256) NOT NULL
     * 
     * @param lengthTypes
     * @param precisionTypes
     * @param field
     * @return
     */
    @NotNull
    public static String newColumnSql(@NotNull final String[] lengthTypes, @NotNull final String[] precisionTypes,
            @NotNull @InstanceOfAny(FieldModel.class) final FieldModel field) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        final String columnType = DB_TYPES.get(field.getColumnType());

        // 2.字段名、数据类型
        sql.append(field.getColumnName()).append(SPACE).append(columnType);
        // 3.包含了length属性的字段构建，先precision，再length
        if (ArraysUtil.contains(precisionTypes, columnType)) {
            sql.append(BRACKET_SL).append(field.getLength()).append(COMMA).append(field.getPrecision())
                    .append(BRACKET_SR);
        } else if (ArraysUtil.contains(lengthTypes, columnType)) {
            sql.append(BRACKET_SL).append(field.getLength()).append(BRACKET_SR);
        }
        // 4.中间空白字符
        sql.append(SPACE);
        // 5.字段是否为空的设置
        if (!field.isNullable() || field.isPrimaryKey()) {
            sql.append(NOT).append(SPACE).append(NULL);
        }
        return sql.toString();
    }

    /**
     * getColType
     * 
     * @param field
     * @return
     */
    @NotNull
    public static String getColType(@NotNull @InstanceOfAny(FieldModel.class) final FieldModel field) {
        return DB_TYPES.get(field.getColumnType());
    }

    /**
     * 生成删除约束的Sql语句：ALTER TABLE TABLE_NAME DROP CONSTRAINT C_NAME
     * 
     * @param table
     * @param name
     * @return
     */
    @NotNull
    public static String dropCSSql(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String name) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.填充模板第二部分
        sql.append(DROP).append(SPACE).append(CONSTRAINT).append(SPACE).append(name);
        // 3.返回最终语句
        return MessageFormat.format(TB_ALTER, table, sql.toString());
    }

    /**
     * 生成删除列的Sql语句：ALTER TABLE TABLE_NAME DROP COLUMN C_NAME
     * 
     * @param table
     * @param name
     * @return
     */
    @NotNull
    public static String dropColSql(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String name) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.填充模板第二部分
        sql.append(DROP).append(SPACE).append(COLUMN).append(SPACE).append(name);
        // 3.返回最终语句
        return MessageFormat.format(TB_ALTER, table, sql.toString());
    }

    /**
     * 生成添加列的Sql语句：ALTER TABLE TABLE_NAME ADD [COLUMN LINE]
     * 
     * @param table
     * @param columnLine
     * @return
     */
    @NotNull
    public static String addColSql(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String columnLine) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.填充模板的第二部分
        sql.append(ADD).append(SPACE).append(columnLine);
        // 3.返回最终语句
        return MessageFormat.format(TB_ALTER, table, sql.toString());
    }

    /**
     * 生成修改列的Sql语句：ALTER TABLE TABLE_NAME ALTER COLULMN [COLUMN LINE]
     * 
     * @param table
     * @param columnLine
     * @return
     */
    @NotNull
    public static String alterColSql(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String columnLine) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.填充模板的第二部分
        sql.append(ALTER).append(SPACE).append(COLUMN).append(SPACE).append(columnLine);
        // 3.返回最终语句
        return MessageFormat.format(TB_ALTER, table, sql.toString());
    }

    /**
     * 生成添加约束的Sql语句：ALTER TABLE TABLE_NAME ADD [CONATRAINT LINE]
     * 
     * @param table
     * @param csLine
     * @return
     */
    @NotNull
    public static String addCSSql(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String csLine) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.填充模板的第二部分
        sql.append(ADD).append(SPACE).append(csLine);
        // 3.返回最终语句
        return MessageFormat.format(TB_ALTER, table, sql.toString());
    }

    /**
     * 生成表统计数据
     * 
     * @param table
     * @return
     */
    @NotNull
    public static String genCountRowsSql(@NotNull @NotEmpty @NotBlank final String table) {
        return MessageFormat.format(TB_COUNT, table);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SqlDdlStatement() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
