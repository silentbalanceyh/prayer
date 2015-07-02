package com.prayer.res.cv;

/**
 * 配置属性文件用的Key，即属性文件中的key=value中的常量key
 * @author Lang
 */
interface PropKeys {

	// ~ Static Fields =======================================
	/**
	 * Default global property file path. *
	 */
	String PROP_FILE = "/global.properties";
	/**
	 * Default schema category to define model. *
	 */
	String SMA_KEY = "schema.category";
	/**
	 * Default strategy implementation class name. *
	 */
	String SMA_STRATEGY_KEY = "schema.{0}.context";
	/**
	 * Global Encoding *
	 */
	String SYS_EN_KEY = "system.en.encoding";
	/**
	 * Global Chinese Encoding *
	 */
	String SYS_CN_KEY = "system.cn.encoding";
	/**
	 * SQL/NOSQL mode of current database. *
	 */
	String DB_MODE_KEY = "database.mode";
	/**
	 * Database connection pool *
	 */
	String DB_POOL = "database.pool.impl";
	/**
	 * Database connection pool class
	 */
	String DB_DATA_SOURCE = "database.pool.datasource";
	/**
	 * Shared in SQL, NoSQL, the category of database *
	 */
	String DB_CATEGORY_KEY = "database.category";
	/**
	 * Database config file path. *
	 */
	String DB_CONFIG_KEY = "database.config.file";
	/**
	 * Initialization SQL file script path. *
	 */
	String DB_INITSQL_KEY = "database.sql.directory";
	/**
	 * Default batch size for sql database. *
	 */
	String DB_BATCH_SIZE_KEY = "database.batch.size";
	/**
	 * Default metadata builder classes configuration *
	 */
	String DB_BUILDER_KEY = "database.meta.builder";
	/**
	 * Default dao classes configuration. *
	 */
	String DB_DAO_KEY = "database.dao";
	/**
	 * Default dao classes configuration. *
	 */
	String DB_ODAO_KEY = "database.oauth.dao";
	/**
	 * Spring核心配置文件路径
	 */
	String TP_CFG_SPRING_KEY = "spring.config.file";
	/**
	 * Mybatis配置文件路径
	 */
	String TP_CFG_MB_KEY = "mybatis.config.file";
	/**
	 * Mybatis使用的环境Environment的ID值
	 */
	String TP_CFG_MB_ENV = "mybatis.environment";
	/**
	 * H2嵌入式数据库URL地址，决定了数据文件的存储位置
	 */
	String DB_H2 = "h2.jdbc.url";

	// ~ Constructors ========================================
}
