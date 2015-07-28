package com.prayer.constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	/** 系统边界值：-1，用于很多地方的临界值使用 **/
	int RANGE = -1;
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
	/** 空集合：仅用于传参 **/
	Set<String> T_STR_SET = new HashSet<>();
	/** 空列表 ：仅用于传参 **/
	List<String> T_STR_LST = new ArrayList<>();
}
