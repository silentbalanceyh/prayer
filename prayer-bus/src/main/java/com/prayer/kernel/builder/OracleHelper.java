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
	/** 数据库配置资源加载器 **/
	private static final PropertyKit LOADER = new PropertyKit(OracleHelper.class, Resources.DB_CFG_FILE);
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
