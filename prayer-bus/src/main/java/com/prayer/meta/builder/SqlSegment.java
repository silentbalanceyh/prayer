package com.prayer.meta.builder;

/**
 * SQL片断常量文件
 * 
 * @author Lang
 *
 */
interface SqlSegment {	// NOPMD
	// ~ 系统字符常量 =======================================
	/** 空白字符 **/
	char SPACE = ' ';
	/** 左小括号 **/
	char BRACKET_SL = '(';
	/** 右小括号 **/
	char BRACKET_SR = ')';

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
	/** COMMA关键字 **/
	String COMMA = ",";
	/** 换行符 **/
	String NEW_LINE = "\n";
	
	// ~ SQL语句模板 ========================================
	/** 创建表的语句模板 **/
	String TB_CREATE = "CREATE TABLE {0} ( {1} )";
	/** 删除表的语句模板 **/
	String TB_DROP = "DROP TABLE {0}";

}