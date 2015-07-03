package com.prayer.res.cv;

/**
 * 
 * @author Lang
 * @see All system constants which must not be changed
 */
public interface Constants {	// NOPMD
	// ~ Static Fields =======================================
	/**
	 * SQL文件名后缀
	 */
	String FEX_SQL = "sql";
	/**
	 * Database mode: SQL *
	 */
	String DM_SQL = "SQL";
	/**
	 * Database mode: NOSQL *
	 */
	String DM_NOSQL = "NOSQL";
	/**
	 * Default database: Microsoft SQL Server *
	 */
	String DF_MSSQL = "MSSQL";
	/**
	 * Default database: Oracle *
	 */
	String DF_ORACLE = "ORACLE";
	/**
	 * Default database: Postgre SQL *
	 */
	String DF_PGSQL = "PGSQL";
	/**
	 * Default database: MySQL *
	 */
	String DF_MYSQL = "MYSQL";
	/**
	 * Policy: ID policy 'AUTO' *
	 */
	String KP_AUTO = "AUTO";
	/**
	 * Policy: ID policy 'GUID' *
	 */
	String KP_GUID = "GUID";
	/**
	 * Policy: ID policy 'RANDOM' *
	 */
	String KP_RANDOM = "RANDOM";
	/**
	 * Policy: ID policy 'MULTI' *
	 */
	String KP_MULTI = "MULTI";
	// ~ System ==============================================
	/** System value 1 **/
	int ONE = 1;
	/** System value 0 **/
	int ZERO = 0;
	// ~ Constructors ========================================
}
