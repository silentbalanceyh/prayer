package com.prayer.dao.impl.builder;

import java.text.MessageFormat;

import com.prayer.constant.Resources;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * SQL Server元数据读取器
 * 
 * @author Lang
 *
 */
@Guarded
final class MsSqlHelper {
    // ~ Static Fields =======================================
    /** 类型后跟Length，类似：{ VARCHAR(256) } **/
    public static final String[] LENGTH_TYPES = new String[] { "CHAR", "VARCHAR", "NCHAR", "NVARCHAR" };
    /** 类型后边跟精度，类似：{ DECIMAL(2,4) } **/
    public static final String[] PRECISION_TYPES = new String[] { "DECIMAL" };
    /** SQL Server 特殊关键字 **/
    public static final String IDENTITY = "IDENTITY";
    /**
     * 检查表是否存在 从视图中读取替换原始读取，SQL Server 2005以上支持这种方式，原始方案：SELECT COUNT(name) FROM
     * dbo.SYSOBJECTS WHERE ID = OBJECT_ID(N''{0}'')
     */
    private final static String SQL_TB_EXIST = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE=''BASE TABLE'' AND TABLE_CATALOG = ''{0}'' AND TABLE_NAME = ''{1}''";
    /** 读取某个表中的所有列名 **/
    private final static String SQL_TB_COLUMN = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}''";
    /** 检查某个表中某个列是否存在 **/
    private final static String SQL_TB_COL_EXIST = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}'' AND COLUMN_NAME=''{2}''";
    /** 获取所有约束名称 **/
    private final static String SQL_TB_CONSTRAINT = "SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}'' ORDER BY CONSTRAINT_NAME";
    /** 检查对应列的约束是否匹配，必须UniqueKey或PrimaryKey **/
    private final static String SQL_TBK_DST_CONS = "SELECT COUNT(*) FROM (INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE AS CC JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC ON CC.CONSTRAINT_NAME = TC.CONSTRAINT_NAME) WHERE CC.TABLE_CATALOG=''{0}'' AND CC.TABLE_NAME=''{1}'' AND (TC.CONSTRAINT_TYPE = ''PRIMARY KEY'' OR TC.CONSTRAINT_TYPE=''UNIQUE'') AND CC.COLUMN_NAME=''{2}''";
    /** 检查对应列的类型是否匹配 **/
    private final static String SQL_TBK_DST_TYPE = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}'' AND COLUMN_NAME=''{2}'' AND DATA_TYPE=''{3}''";
    /** 列空值检测 **/
    private final static String SQL_TB_NULL = "SELECT COUNT(*) FROM {0} WHERE {1} IS NULL";
    /** **/
    private final static String SQL_TB_UNIQUE = "SELECT COUNT(DISTINCT {1}) FROM {0} WHERE {1} IN (SELECT {1} FROM {0} GROUP BY {1} HAVING COUNT({1}) > 1)";
    /** 数据库配置资源加载器 **/
    private static final PropertyKit LOADER = new PropertyKit(MsSqlHelper.class, Resources.DB_CFG_FILE);

    /** **/
    public final static String COL_TB_COLUMN = "COLUMN_NAME"; // new
                                                              // String[]{"COLUMN_NAME"};
    /** **/
    public final static String COL_TB_CONSTRAINT = "CONSTRAINT_NAME"; // new
                                                                      // String[]{"CONSTRAINT_NAME"};
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 统计系统中的列类型是否匹配
     * @param tableName
     * @param column
     * @param type
     * @return
     */
    @NotNull
    public static String getSqlColumnType(@NotNull @NotBlank @NotEmpty final String tableName,
            @NotNull @NotBlank @NotEmpty final String column, @NotNull @NotBlank @NotEmpty final String type) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TBK_DST_TYPE, database, tableName, column, type);
    }

    /**
     * 统计系统中的表的SQL
     * 
     * @param tableName
     * @return
     */
    @NotNull
    public static String getSqlTableExist(@NotNull @NotBlank @NotEmpty final String tableName) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TB_EXIST, database, tableName);
    }

    /**
     * 检查表中字段是否存在
     * 
     * @param tableName
     * @param column
     * @return
     */
    @NotNull
    public static String getSqlColumnExist(@NotNull @NotBlank @NotEmpty final String tableName,
            @NotNull @NotBlank @NotEmpty final String column) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TB_COL_EXIST, database, tableName, column);
    }

    /**
     * 检查外键是否Unique或者Primary Key
     * 
     * @param tableName
     * @param column
     * @return
     */
    @NotNull
    public static String getSqlUKPKConstraint(@NotNull @NotBlank @NotEmpty final String tableName,
            @NotNull @NotBlank @NotEmpty final String column) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TBK_DST_CONS, database, tableName, column);
    }

    /**
     * 获取系统中列集合的SQL
     * 
     * @param tableName
     * @return
     */
    @NotNull
    public static String getSqlColumns(@NotNull @NotBlank @NotEmpty final String tableName) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TB_COLUMN, database, tableName);
    }

    /**
     * 获取系统中的约束的SQL
     * 
     * @param tableName
     * @return
     */
    @NotNull
    public static String getSqlConstraints(@NotNull @NotBlank @NotEmpty final String tableName) {
        final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
        return MessageFormat.format(SQL_TB_CONSTRAINT, database, tableName);
    }

    /**
     * 检查表中指定列是否有空数据的SQL语句
     * 
     * @param tableName
     * @param colName
     * @return
     */
    @NotNull
    public static String getSqlNull(@NotNull @NotBlank @NotEmpty final String tableName,
            @NotNull @NotBlank @NotEmpty final String colName) {
        return MessageFormat.format(SQL_TB_NULL, tableName, colName);
    }

    /**
     * 检查表中某个字段是否有重复数据
     * 
     * @param tableName
     * @param colName
     * @return
     */
    @NotNull
    public static String getSqlUnique(@NotNull @NotBlank @NotEmpty final String tableName,
            @NotNull @NotBlank @NotEmpty final String colName) {
        return MessageFormat.format(SQL_TB_UNIQUE, tableName, colName);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private MsSqlHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
