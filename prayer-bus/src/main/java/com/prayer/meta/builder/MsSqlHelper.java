package com.prayer.meta.builder;

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
	
	private final static String SQL_TB_META = "";

	/** 数据库配置资源加载器 **/
	private static final PropertyKit LOADER = new PropertyKit(MsSqlHelper.class, Resources.DB_CFG_FILE);

	// AND OBJECTPROPERTY(ID, ''IsTable'') = 1";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getSqlTableExist(@NotNull final String tableName) {
		final String database = LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
		return MessageFormat.format(SQL_TB_EXIST, database, tableName);
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
