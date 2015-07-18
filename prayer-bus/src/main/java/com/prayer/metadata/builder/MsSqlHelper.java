package com.prayer.metadata.builder;

import java.text.MessageFormat;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

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
	/** **/
	private final static String SQL_TB_COLUMN = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}''";
	/** **/
	private final static String SQL_TB_CONSTRAINT = "SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_CATALOG=''{0}'' AND TABLE_NAME=''{1}'' ORDER BY CONSTRAINT_NAME";
	/** 列空值检测 **/
	private final static String SQL_TB_NULL = "SELECT COUNT(*) FROM {0} WHERE {1} IS NULL";
	/** 数据库配置资源加载器 **/
	private static final PropertyKit LOADER = new PropertyKit(MsSqlHelper.class, Resources.DB_CFG_FILE);
	
	/** **/
	public final static String COL_TB_COLUMN = "COLUMN_NAME"; // new String[]{"COLUMN_NAME"};
	/** **/
	public final static String COL_TB_CONSTRAINT = "CONSTRAINT_NAME"; // new String[]{"CONSTRAINT_NAME"};
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 统计系统中的表的SQL
	 * @param tableName
	 * @return
	 */
	public static String getSqlTableExist(@NotNull final String tableName) {
		final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
		return MessageFormat.format(SQL_TB_EXIST, database, tableName);
	}

	/**
	 * 获取系统中列集合的SQL
	 * @param tableName
	 * @return
	 */
	public static String getSqlColumns(@NotNull final String tableName) {
		final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
		return MessageFormat.format(SQL_TB_COLUMN, database, tableName);
	}
	/**
	 * 获取系统中的约束的SQL
	 * @param tableName
	 * @return
	 */
	public static String getSqlConstraints(@NotNull final String tableName){
		final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
		return MessageFormat.format(SQL_TB_CONSTRAINT, database, tableName);
	}
	/**
	 * 检查表中指定列是否有空数据的SQL语句
	 * @param tableName
	 * @param colName
	 * @return
	 */
	public static String getSqlNull(@NotNull final String tableName, @NotNull final String colName){
		return MessageFormat.format(SQL_TB_NULL, tableName, colName);
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
