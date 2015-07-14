package com.prayer.meta.builder;

import java.text.MessageFormat;

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

	/** 检查表是否存在 **/
	private final static String SQL_TB_EXIST = "SELECT COUNT(name) FROM dbo.SYSOBJECTS WHERE ID = OBJECT_ID(N''{0}'') AND OBJECTPROPERTY(ID, ''IsTable'') = 1";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getSqlTableExist(@NotNull final String tableName){
		return MessageFormat.format(SQL_TB_EXIST, tableName);
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private MsSqlHelper(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
