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
	/** 数据库SQL模式 **/
	String DB_MODE_SQL = "SQL";
	/** 数据库NOSQL模式 **/
	String DB_MODE_NOSQL = "NOSQL";

	/** OVol 默认脚本 **/
	String LANG_GROOVY = "groovy";
	/** Script Engine 名称 **/
	String SCRIPT_ENGINE = "nashorn";
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

	// ~ Vertx Default =======================================
	/** HA Group Default **/
	String VX_GROUP = "__DEFAULT__";
	/** 默认的顺序，但不设置，作为临界值 **/
	int VX_DF_ORDER = -10000;
	/** Global Error Key **/
	String VX_CTX_ERROR = "FAILURE.KEY";
	/** Global URI Key **/
	String VX_CTX_URI = "URI.ENTITY";
	/** Request Parameters **/
	String VX_CTX_PARAMS = "PARAMS";
	/** Default Convertor，默认Convertor，什么都不做 **/
	String VX_DF_CONVERTOR = "__DEFAULT__";
	/** 根目录对应的情况，去除掉/static/*静态目录 **/
	String VX_API_ROOT = "/api/*";
	/** Api的远程地址 **/
	String VX_API_DK = "API.URL";
	/** Secure API的根目录 **/
	String VX_SECURE_API_ROOT = "/api/sec/*";	// NOPMD
	/** Static静态资源 **/
	String VX_STATIC_ROOT = "/static/*";
	/** favicon.ico默认路径 **/
	String VX_FAVICON_ROOT = "/favicon.ico";
	/** favicon.ico路径 **/
	String VX_FAVICON_PATH = "/static/favicon.ico";
	/** Template需要使用的资源 **/
	String VX_DYNAMIC_ROOT = "/dynamic/*";
	/** 基础认证的Redirect **/
	String VX_DYNAMIC_ADMIN = "/dynamic/admin/*";
	/** 登录界面 **/
	String VX_LOGIN_PAGE = "/dynamic/index.jade";
	/** Global ID对应Key **/
	String BUS_GLOBAL_ID = "identifier";
	/** Script Name对应Key **/
	String BUS_SCRIPT_NAME = "script";
	/** Param Data **/
	String BUS_DATA = "data";
	// ~ Vertx 参数信息 ======================================
	/** **/
	String PARAM_ID = "identifier";
	/** **/
	String PARAM_SCRIPT = "script";
	/** **/
	String PARAM_DATA = "data";
	/** **/
	String PARAM_SESSION = "session.id";
	/** **/
	String PARAM_METHOD = "method";
	/** **/
	String PARAM_FILTERS = "filters";
	// ~ Vertx Order =========================================
	/** CORS Handler **/
	int VX_OD_CORS = -990;
	/** Cookie Handler Order **/
	int VX_OD_COOKIE = -980;
	/** Body Handler Order **/
	int VX_OD_BODY = -970;
	/** Authentication Handler Order **/
	int VX_OD_AUTH = -960;
	/** Session Handler Order **/
	int VX_OD_SESSION = -950;
	/** Redirect之前的预处理 **/
	int VX_OD_SHARED = -945;
	/** User Session Handler Order **/
	int VX_OD_USER_SESSION = -940;		// NOPMD
	/** Redirect Handler Order **/
	int VX_OD_ADMIN = -930;
	/** Static Handler **/
	int VX_OD_STATIC = -920;
	/** Template Handler **/
	int VX_OD_DYNAMIC = -910;
	/** **/
	int VX_OD_CONTEXT = -900;

	/** Router Handler Order **/
	int VX_OD_ROUTER = -400;
	/** Validation Handler Order **/
	int VX_OD_VALIDATION = -300;
	/** Convertor Handler Order **/
	int VX_OD_CONVERTOR = -200;
	/** Parameters Wrapper **/
	int VX_OD_WRAPPER = -100;
	/** Service Handler Order **/
	int VX_OD_SERVICE = 100;

	/** Failure Handler **/
	int VX_OD_FAILURE = 200;
	// ~ Web Failure =========================================

	/** Status Code: 404,501 etc. **/
	String STATUS_CODE = "statusCode";
	/** Status Code Error: NOT_FOUND, INTERNAL_ERROR etc. **/
	String ERROR = "error";
	/** Error Message: Exception description **/
	String ERROR_MSG = "errorMessage";
	/** SUCCESS/FAILURE/ERROR **/
	String RESPONSE = "response";
}
