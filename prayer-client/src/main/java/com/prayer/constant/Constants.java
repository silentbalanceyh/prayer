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
	/** **/
	String PROP_SERVER = "/server.properties";
	/** **/
	String PROP_VERX = "/vertx.properties";
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
	/** Default Buffer Size **/
	int BUFFER_SIZE = 32;
	// ~ Simple Response Code ================================

	/** 成功返回值 **/
	int RC_SUCCESS = ZERO;
	/** 失败返回值 **/
	int RC_FAILURE = -1;
	// ~ Type Constants ======================================
	/** 字符串数组类型 **/
	String[] T_STR_ARR = new String[] {};
	/** 用于传参数 **/
	Object[] T_OBJ_ARR = new Object[] {};
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

	// ~ Util ================================================
	/** 16进制字节数组 **/
	char[] UTI_HEX_ARR = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	// ~ Order ===============================================
	
	/** 默认的顺序，但不设置，作为临界值 **/
	int NOT_SET = -10000;
	/** Cookie Handler Order **/
	int COOKIE = -980;
	/** Body Handler Order **/
	int BODY = -970;
	/** Static **/
	int STATIC = -960;
	/** Session Handler Order **/
	int SESSION = -950;
	/** Redirect之前的预处理 **/
}
