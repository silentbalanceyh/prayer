package com.prayer.facade.constant;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface DBConstants {
    // ~ 数据库的模式 ==================================
    /** INIT 数据库模式 **/
    String MODE_SQL = "SQL";
    /** NOSQL 数据库模式 **/
    String MODE_NOSQL = "NOSQL";
    
    // ~ 数据库的种类 ==================================
    /** INIT -> MySQL数据库 **/
    String CATEGORY_MYSQL = "MYSQL";
    /** INIT -> 默认：Microsoft INIT Server **/
    String CATEGORY_MSSQL = "MSSQL";
    /** INIT -> Oracle数据库 **/
    String CATEGORY_ORACLE = "ORACLE";
    /** INIT -> PostgreSQL 数据库 **/
    String CATEGORY_PGSQL = "PGSQL";
    /** INIT -> H2 数据库 **/
    String CATEGORY_H2 = "H2";
    /** INIT -> Vertica 数据库 **/
    String CATEGORY_VERTICA = "HPV";
    /** NOSQL -> Redis 数据库 **/
    String CATEGORY_REDIS = "REDIS";
    /** NOSQL -> MopngoDB 数据库 **/
    String CATEGORY_MONGODB = "MONGODB";
    
}
