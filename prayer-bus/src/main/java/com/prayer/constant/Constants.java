package com.prayer.constant;

/**
 * 
 * @author Lang
 * @see All system constants which must not be changed
 */
public interface Constants { // NOPMD
	// ~ Static Fields =======================================
	/** 数据库SQL模式　**/
	String DB_MODE_SQL = "SQL";
	/** 数据库NOSQL模式  **/
	String DB_MODE_NOSQL = "NOSQL";
	
	/** OVol 默认脚本 **/
	String LANG_GROOVY = "groovy";
	// ~ System ==============================================
	/** System value 2 **/
	int TWO = 2;
	/** System value 1 **/
	int ONE = 1;
	/** System value 0 **/
	int ZERO = 0;
	/** HashCode base number **/
	int HASH_BASE = 31;
	// ~ Simple Response Code ================================
	
	/** 成功返回值 **/
	int RC_SUCCESS = ZERO;
	/** 失败返回值 **/
	int RC_FAILURE = -1;
	// ~ Type Constants ======================================
	/** 字符串数组类型 **/
	String[] T_STR_ARR = new String[]{};
}
