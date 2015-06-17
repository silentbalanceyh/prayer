package com.prayer.res;

/**
 * 
 * @author Lang
 * @see All system constants which must not be changed
 */
public final class Constants {
	// ~ Static Fields =======================================
	/**
	 * SQL文件名后缀
	 */
	public static final String FEX_SQL = "sql";
	/**
	 * Database mode: SQL *
	 */
	public static final String DM_SQL = "SQL";
	/**
	 * Database mode: NOSQL *
	 */
	public static final String DM_NOSQL = "NOSQL";
	/**
	 * Default database: Microsoft SQL Server *
	 */
	public static final String DF_MSSQL = "MSSQL";
	/**
	 * Default database: Oracle *
	 */
	public static final String DF_ORACLE = "ORACLE";
	/**
	 * Default database: Postgre SQL *
	 */
	public static final String DF_PGSQL = "PGSQL";
	/**
	 * Default database: MySQL *
	 */
	public static final String DF_MYSQL = "MYSQL";
	/**
	 * Policy: ID policy 'AUTO' *
	 */
	public static final String KP_AUTO = "AUTO";
	/**
	 * Policy: ID policy 'GUID' *
	 */
	public static final String KP_GUID = "GUID";
	/**
	 * Policy: ID policy 'RANDOM' *
	 */
	public static final String KP_RANDOM = "RANDOM";
	/**
	 * Policy: ID policy 'MULTI' *
	 */
	public static final String KP_MULTI = "MULTI";

	// ~ Constructors ========================================
	private Constants() {
	}
}
