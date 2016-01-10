package com.prayer.constant;
/**
 * 
 * @author Lang
 *
 */
public interface DBConstants {
    // ~ 数据库的模式 ==================================
    /** SQL 数据库模式 **/
    String MODE_SQL = "SQL";
    /** NOSQL 数据库模式 **/
    String MODE_NOSQL = "NOSQL";
    
    // ~ 数据库的种类 ==================================
    /** SQL -> MySQL数据库 **/
    String CATEGORY_MYSQL = "MYSQL";
    /** SQL -> 默认：Microsoft SQL Server **/
    String CATEGORY_MSSQL = "MSSQL";
    /** SQL -> Oracle数据库 **/
    String CATEGORY_ORACLE = "ORACLE";
    /** SQL -> PostgreSQL 数据库 **/
    String CATEGORY_PGSQL = "PGSQL";
    /** SQL -> H2 数据库 **/
    String CATEGORY_H2 = "H2";
    /** SQL -> Vertica 数据库 **/
    String CATEGORY_VERTICA = "HPV";
    /** NOSQL -> Redis 数据库 **/
    String CATEGORY_REDIS = "REDIS";
    /** NOSQL -> MopngoDB 数据库 **/
    String CATEGORY_MONGODB = "MONGODB";
    
}
