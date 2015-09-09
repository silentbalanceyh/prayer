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
	/** 用于传参数 **/
	Object[] T_OBJ_ARR = new Object[]{};
	/** 空集合：仅用于传参 **/
	Set<String> T_STR_SET = new HashSet<>();
	/** 空列表 ：仅用于传参 **/
	List<String> T_STR_LST = new ArrayList<>();
	
	// ~ Spec Literal ========================================
	/** Json空对象 **/
	String EMPTY_JOBJ = "{}";
	/** Json空数组 **/
	String EMPTY_JARR = "[]";
	/** String空字符串 **/
	String EMPTY_STR = "";
	
	// ~ Vertx Default =======================================
	/** HA Group Default **/
	String VX_GROUP = "__DEFAULT__";
	/** 默认的顺序，但不设置，作为临界值 **/
	int VX_DEFAULT_ORDER = -10000;
	/** Global Error Key **/
	String VX_CTX_ERROR = "FAILURE.KEY";
	/** Global URI Key **/
	String VX_CTX_URI_ID = "URI.ID";
	/** Request Parameters **/
	String VX_CTX_PARAMS = "PARAMS";
	
	// ~ Vertx Order =========================================
	/** Body Handler Order **/
	int VX_OD_BODY = -9;
	/** Cookie Handler Order **/
	int VX_OD_COOKIE = -10;
	/** Session Handler Order **/
	int VX_OD_SESSION = -7;
	/** Authentication Handler Order **/
	int VX_OD_AUTH = -8;
	/** Router Handler Order **/
	int VX_OD_ROUTER = -5;
	/** Validation Handler Order **/
	int VX_OD_VALIDATION = -4;
	/** Convertor Handler Order **/
	int VX_OD_CONVERTOR = -3;
	/** Failure Handler **/
	int VX_OD_FAILURE = 1;
}
