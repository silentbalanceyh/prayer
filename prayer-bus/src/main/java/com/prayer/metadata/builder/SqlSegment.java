package com.prayer.metadata.builder;

/**
 * SQL片断常量文件
 * 
 * @author Lang
 *
 */
interface SqlSegment {	// NOPMD
	// ~ SQL关键字 ==========================================
	/** NOT关键字 **/
	String NOT = "NOT";
	/** NULL关键字 **/
	String NULL = "NULL";
	/** CONSTRAINT关键字 **/
	String CONSTRAINT = "CONSTRAINT";
	/** UNIQUE关键字 **/
	String UNIQUE = "UNIQUE";
	/** PRIMARY关键字 **/
	String PRIMARY = "PRIMARY";
	/** KEY关键字 **/
	String KEY = "KEY";
	/** FOREIGN关键字 **/
	String FOREIGN = "FOREIGN";
	/** REFERENCES关键字 **/ 
	String REFERENCES = "REFERENCES";
	/** DROP关键字 **/
	String DROP = "DROP";
	/** COLUMN关键字 **/
	String COLUMN = "COLUMN";
	/** ADD关键字 **/
	String ADD = "ADD";
	/** WHERE关键字 **/
	String WHERE = "WHERE";
	/** ALTER关键字 **/
	String ALTER = "ALTER";
	
	// ~ SQL语句模板 ========================================
	/** 创建表的语句模板 **/
	String TB_CREATE = "CREATE TABLE {0} ( {1} )";
	/** 删除表的语句模板 **/
	String TB_DROP = "DROP TABLE {0}";
	/** 修改表的语句模板 **/
	String TB_ALTER = "ALTER TABLE {0} {1}";
	
	/** 统计表中有多少行数据 **/
	String TB_COUNT = "SELECT COUNT(*) FROM {0}";
}
