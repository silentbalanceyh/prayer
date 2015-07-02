package com.prayer.res.cv;

/**
 * @author Lang
 * @package com.prayer.res.cv
 * @name Classes
 * @class com.prayer.res.cv.Classes
 * @date Nov 28, 2014 4:48:50 PM
 * @see All class names of constant value
 */
interface Classes {

	// ~ Static Fields =======================================
	/**
	 * Default builder classes' package full name. *
	 */
	String BLD_PKG = "com.prayer.meta.builder.impl";
	/**
	 * Default builder class name for SQL Server.
	 */
	String BLD_MSSQL = BLD_PKG + ".MsSqlBuilder";
	/**
	 * Default builder class name for Oracle.
	 */
	String BLD_ORACLE = BLD_PKG + ".OracleBuilder";
	/**
	 * Default builder class name for MySql.
	 */
	String BLD_MYSQL = BLD_PKG + ".MySqlBuilder";
	/**
	 * Default builder class name for Postgre SQL.
	 */
	String BLD_PGSQL = BLD_PKG + ".PgSqlBuilder";

	/**
	 * Default Dao component classes' package full name. *
	 */
	String DAO_PKG = "com.prayer.db.sql.impl";
	/**
	 * Default Dao class name for SQL Server.
	 */
	String DAO_MSSQL = DAO_PKG + ".MsSqlDao";
	/**
	 * Default Dao class name for Oracle.
	 */
	String DAO_ORACLE = DAO_PKG + ".OracleDao";
	/**
	 * Default Dao class name for MySql.
	 */
	String DAO_MYSQL = DAO_PKG + ".MySqlDao";
	/**
	 * Default Dao class name for PgSql.
	 */
	String DAO_PGSQL = DAO_PKG + ".PgSqlDao";

	/**
	 * Default Dao component classes' package full name. *
	 */
	String DAO_OAUTH_PKG = "com.prayer.db.sql.sec.impl";
	/**
	 * Default Security Dao class name for SQL Server. *
	 */
	String DAO_OAUTH_MSSQL = DAO_OAUTH_PKG
			+ ".MsSqlSecurityDao";
	/**
	 * Default connection pool package *
	 */
	String DBP_PKG = "com.prayer.db.pool";
	/**
	 * Default connection Pool *
	 */
	String DBP_BONECP = DBP_PKG + ".BoneCPContext";

	/**
	 * Default schema context *
	 */
	String CTX_CONTEXT = "com.prayer.meta.impl.GenericContext";

	// ~ Constructors ========================================
}
