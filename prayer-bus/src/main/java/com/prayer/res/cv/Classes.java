package com.prayer.res.cv;

/**
 * @author Lang
 * @package com.prayer.res.cv
 * @name Classes
 * @class com.prayer.res.cv.Classes
 * @date Nov 28, 2014 4:48:50 PM
 * @see All class names of constant value
 */
final class Classes {

	// ~ Static Fields =======================================
	/**
	 * Default builder classes' package full name. *
	 */
	private static final String BLD_PKG = "com.prayer.meta.builder.impl";
	/**
	 * Default builder class name for SQL Server.
	 */
	public static final String BLD_MSSQL = BLD_PKG + ".MsSqlBuilder";
	/**
	 * Default builder class name for Oracle.
	 */
	public static final String BLD_ORACLE = BLD_PKG + ".OracleBuilder";
	/**
	 * Default builder class name for MySql.
	 */
	public static final String BLD_MYSQL = BLD_PKG + ".MySqlBuilder";
	/**
	 * Default builder class name for Postgre SQL.
	 */
	public static final String BLD_PGSQL = BLD_PKG + ".PgSqlBuilder";

	/**
	 * Default Dao component classes' package full name. *
	 */
	private static final String DAO_PKG = "com.prayer.db.sql.impl";
	/**
	 * Default Dao class name for SQL Server.
	 */
	public static final String DAO_MSSQL = DAO_PKG + ".MsSqlDao";
	/**
	 * Default Dao class name for Oracle.
	 */
	public static final String DAO_ORACLE = DAO_PKG + ".OracleDao";
	/**
	 * Default Dao class name for MySql.
	 */
	public static final String DAO_MYSQL = DAO_PKG + ".MySqlDao";
	/**
	 * Default Dao class name for PgSql.
	 */
	public static final String DAO_PGSQL = DAO_PKG + ".PgSqlDao";

	/**
	 * Default Dao component classes' package full name. *
	 */
	private static final String DAO_OAUTH_PKG = "com.prayer.db.sql.sec.impl";
	/**
	 * Default Security Dao class name for SQL Server. *
	 */
	public static final String DAO_OAUTH_MSSQL = DAO_OAUTH_PKG
			+ ".MsSqlSecurityDao";
	/**
	 * Default connection pool package *
	 */
	private static final String DBP_PKG = "com.prayer.db.pool";
	/**
	 * Default connection Pool *
	 */
	public static final String DBP_BONECP = DBP_PKG + ".BoneCPContext";

	/**
	 * Default schema context *
	 */
	public static final String CTX_CONTEXT = "com.prayer.meta.impl.GenericContext";

	// ~ Constructors ========================================
	private Classes() {
	}
}
