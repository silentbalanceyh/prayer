/**
 * 
 */
package com.prayer.kernel.builder;

import java.text.MessageFormat;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * @author huar
 *
 */
@Guarded
public final class OracleHelper {
	// ~ Static Fields =======================================
	/** 类型后跟Length，类似：{ VARCHAR(256) } **/
	public static final String[] LENGTH_TYPES = new String[] { "CHAR", "VARCHAR", "NCHAR", "NVARCHAR", "VARCHAR2", "NUMBER" };
	/** 类型后边跟精度，类似：{ DECIMAL(2,4) } **/
	public static final String[] PRECISION_TYPES = new String[] { "NUMBER" };
	/**
	 * Exiting table checking SQL statement. *
	 */
	private final static String SQL_TB_EXIST = "SELECT COUNT(1) FROM ALL_TABLES WHERE OWNER=''{0}'' AND TABLE_NAME=''{1}''";
	/** **/
	private final static String SQL_TB_COLUMN = "SELECT COLUMN_NAME,NULLABLE,DATA_TYPE,DATA_LENGTH FROM ALL_TAB_COLUMNS WHERE OWNER=''{0}'' AND TABLE_NAME=''{1}''";
	/** 增加 distinct 出除重复的约束名**/
	private final static String SQL_TB_CONSTRAINT = "SELECT DISTINCT T.CONSTRAINT_NAME,C.CONSTRAINT_TYPE FROM USER_CONSTRAINTS C,USER_CONS_COLUMNS T WHERE T.CONSTRAINT_NAME=C.CONSTRAINT_NAME AND T.OWNER=''{0}'' AND T.TABLE_NAME=''{1}'' ORDER BY T.CONSTRAINT_NAME";
	/** 列空值检测 **/
	private final static String SQL_TB_NULL = "SELECT COUNT(*) FROM {0} WHERE {1} IS NULL";
	
	/** 数据库配置资源加载器 **/
	private static final PropertyKit LOADER = new PropertyKit(OracleHelper.class, Resources.DB_CFG_FILE);
	
	/** **/
	public final static String COL_TB_COLUMN = "COLUMN_NAME";
	/** **/
	public final static String COL_TB_CONSTRAINT = "CONSTRAINT_NAME";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
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
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private OracleHelper() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
