package com.prayer.constant;

import static com.prayer.util.Error.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.PropertyKit;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Static resource loeader to read configuration
 *
 * @author Lang
 */
@Guarded
public final class Resources { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Resources.class);
	/**
	 * Error mapping property file path
	 */
	public static final String ERR_PROP_FILE = "/err.properties";
	/**
	 * Schema mode of current system, default is: orb.schema=json
	 */
	public static final String SCHEMA_MODE;
	/**
	 * System: Encoding method *
	 */
	public static final String SYS_ENCODING;
	/**
	 * System: Chinese encoding for current system. *
	 */
	public static final String SYS_CN_ENCODING;
	/**
	 * Database: The database pool implementation class
	 */
	public static final String DB_DATA_SOURCE;
	/**
	 * Database: The database mode for current system *
	 */
	public static final String DB_MODE;
	/**
	 * Database: The system database name, default is: mssql *
	 */
	public static final String DB_CATEGORY;
	/**
	 * Database: The system database configuration properties file path. *
	 */
	public static final String DB_CFG_FILE;
	/**
	 * Database: The database builder java class name. *
	 */
	public static final String DB_BUILDER;
	/**
	 * Database: The database record service java class name. *
	 */
	public static final String DB_R_DAO;
	/**
	 * Database: The database oauth service java class name. *
	 */
	public static final String DB_SEC_O_DAO;
	/**
	 * Database: The database init sql file. *
	 */
	public static final String DB_SQL_DIR;
	/**
	 * Database: The database default batch size. *
	 */
	public static final int DB_BATCH_SIZE;
	/**
	 * Spring配置文件路径
	 */
	public static final String T_CFG_SPRING;
	/**
	 * Mybatis配置文件路径
	 */
	public static final String T_CFG_MYBATIS;
	/**
	 * Mybatis环境文件
	 */
	public static final String T_CFG_MB_ENV;
	/**
	 * 
	 */
	public static final String DB_H2;
	/**
	 * Private singleton resource LOADER. *
	 */
	private static final PropertyKit LOADER;

	// ~ Static Block ========================================

	/** Static Loading */
	static {
		/**
		 * 因为PropertyLoader内部已经实现了Properties的单件模式，这里没有必要使用反射的方式创建
		 */
		LOADER = new PropertyKit(PropKeys.class, PropKeys.PROP_FILE); // singleton(PropertyKit.class,
																		// PropKeys.class,
																		// PropKeys.PROP_FILE);
		if (null == LOADER) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "LOADER", LOADER);
		}

		SCHEMA_MODE = LOADER.getString(PropKeys.SMA_KEY);
		if (null == SCHEMA_MODE) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "SCHEMA_MODE", SCHEMA_MODE);
		}
		// System default encoding method
		SYS_ENCODING = LOADER.getString(PropKeys.SYS_EN_KEY);
		if (null == SYS_ENCODING) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "SYS_ENCODING", SYS_ENCODING);
		}
		// System encoding method for Chinese charactors only
		SYS_CN_ENCODING = LOADER.getString(PropKeys.SYS_CN_KEY);
		if (null == SYS_CN_ENCODING) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "SYS_CN_ENCODING", SYS_CN_ENCODING);
		}

		DB_MODE = null == LOADER.getString(PropKeys.DB_MODE_KEY) ? Constants.DM_SQL
				: StringUtil.toUpperCase(LOADER.getString(PropKeys.DB_MODE_KEY));
		if (null == DB_MODE) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_MODE", DB_MODE);
		}

		DB_CATEGORY = null == LOADER.getString(PropKeys.DB_CATEGORY_KEY) ? Constants.DF_MSSQL
				: StringUtil.toUpperCase(LOADER.getString(PropKeys.DB_CATEGORY_KEY));
		if (null == DB_CATEGORY) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_CATEGORY", DB_CATEGORY);
		}

		DB_BUILDER = LOADER.getString(PropKeys.DB_BUILDER_KEY);
		if (null == DB_BUILDER) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_BUILDER", DB_BUILDER);
		}

		DB_R_DAO = LOADER.getString(PropKeys.DB_DAO_KEY);
		if (null == DB_R_DAO) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_R_DAO", DB_R_DAO);
		}

		DB_SEC_O_DAO = LOADER.getString(PropKeys.DB_ODAO_KEY);
		if (null == DB_SEC_O_DAO) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_SEC_O_DAO", DB_SEC_O_DAO);
		}

		DB_CFG_FILE = LOADER.getString(PropKeys.DB_CONFIG_KEY);
		if (null == DB_CFG_FILE) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_CFG_FILE", DB_CFG_FILE);
		}

		DB_SQL_DIR = LOADER.getString(PropKeys.DB_INITSQL_KEY);
		if (null == DB_SQL_DIR) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_SQL_DIR", DB_SQL_DIR);
		}

		DB_BATCH_SIZE = LOADER.getInt(PropKeys.DB_BATCH_SIZE_KEY);
		if (-1 == DB_BATCH_SIZE) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_BATCH_SIZE", DB_BATCH_SIZE);
		}

		DB_DATA_SOURCE = LOADER.getString(PropKeys.DB_DATA_SOURCE);
		if (null == DB_DATA_SOURCE) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_DATA_SOURCE", DB_DATA_SOURCE);
		}

		DB_H2 = LOADER.getString(PropKeys.DB_H2);
		if (null == DB_H2) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "DB_H2", DB_H2);
		}

		T_CFG_SPRING = LOADER.getString(PropKeys.TP_CFG_SPRING_KEY);
		if (null == T_CFG_SPRING) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "T_CFG_SPRING", T_CFG_SPRING);
		}

		T_CFG_MYBATIS = LOADER.getString(PropKeys.TP_CFG_MB_KEY);
		if (null == T_CFG_MYBATIS) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "T_CFG_MYBATIS", T_CFG_MYBATIS);
		}

		T_CFG_MB_ENV = LOADER.getString(PropKeys.TP_CFG_MB_ENV);
		if (null == T_CFG_MB_ENV) {
			debug(LOGGER, PropKeys.ERR_SYS_NULL, "T_CFG_MB_ENV", T_CFG_MB_ENV);
		}
	}

	// ~ Constructors ========================================

	/**
	 * Private constructor to prevent created directly *
	 */
	private Resources() {
	}

	// ~ Static Methods ======================================
	/**
	 * 获取数据库Loader，返回值不能为NULL
	 * 
	 * @return
	 */
	@NotNull
	public static PropertyKit getLoader() {
		return LOADER;
	}
}
