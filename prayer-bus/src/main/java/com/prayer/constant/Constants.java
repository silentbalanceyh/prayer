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
    /** Default Convertor，默认Convertor，什么都不做 **/
    String VX_CONVERTOR = VX_GROUP;
    
    interface ACTION{    // NOPMD
        /** 登录系统的Action地址 **/
        String LOGOUT = "/dynamic/logout";
        /** 登录界面 **/
        String LOGIN_PAGE = "/dynamic/index";
    }

    interface KEY{    // NOPMD
        /** Global Error Key **/
        String CTX_ERROR = "FAILURE.KEY";
        /** Global URI Key **/
        String CTX_URI = "URI.ENTITY";
        /** Request Parameters **/
        String CTX_PARAMS = "PARAMS";
        
        /** Api的远程地址 **/
        String API_URL = "API.URL";
    }

    /** **/
    interface WEB{    // NOPMD
        /** Static静态资源 **/
        String STATIC_ROUTE = "/static/*";
        
        /** Template需要使用的资源 **/
        String DYNAMIC_ROUTE = "/dynamic/*";
        /** 基础认证的Redirect **/
        String DYNAMIC_ADMIN = "/dynamic/admin/*";
        
        /** Options **/
        String DYNAMIC_OPTIONS = "/dynamic/admin/options/*";
        /** Profile **/
        String DYNAMIC_PROFILE = "/dynamic/admin/profile";
        
        /** favicon.ico默认路径 **/
        String FAVICON_ICO = "/favicon.ico";
        /** favicon.ico路径 **/
        String FAVICON_PATH = "/static/favicon.ico";
    }

    /** **/
    interface ROUTE {    // NOPMD
        /** API根目录 **/
        String API = "/api/*";
        /** Secure API的根目录 **/
        String SECURE_API = "/api/sec/*"; // NOPMD
    }

    /** **/
    interface ORDER { // NOPMD
        /** 默认的顺序，但不设置，作为临界值 **/
        int NOT_SET = -10000;

        /** CORS Handler **/
        int CORS = -990;
        /** Cookie Handler Order **/
        int COOKIE = -980;
        /** Body Handler Order **/
        int BODY = -970;
        /** Authentication Handler Order **/
        int AUTH = -960;
        /** Session Handler Order **/
        int SESSION = -950;
        /** Redirect之前的预处理 **/

        int SHARED = -945;
        /** User Session Handler Order **/
        int USER_SESSION = -940; // NOPMD
        /** Logout **/
        int LOGOUT = -935;
        /** Redirect Handler Order **/
        int ADMIN = -930;
        /** Static Handler **/
        int STATIC = -920;
        /** Template Handler **/
        int DYNAMIC = -910;
        /** Context **/
        int CONTEXT = -905;
        /** Target **/
        int DISPLAY = -925;

        /** Router Handler Order **/
        int ROUTER = -400;
        /** Validation Handler Order **/
        int VALIDATION = -300;
        /** Convertor Handler Order **/
        int CONVERTOR = -200;
        /** Parameters Wrapper **/
        int WRAPPER = -100;
        /** Service Handler Order **/
        int SERVICE = 100;

        /** Failure Handler **/
        int FAILURE = 200;
    }
    /** **/
    interface BUS { // NOPMD
        /** Global ID对应Key **/
        String ID = PARAM.ID; // NOPMD
        /** Script Name对应Key **/
        String SCRIPT = PARAM.SCRIPT;
        /** Param Data **/
        String DATA = PARAM.DATA;
    }

    /** **/
    interface RET { // NOPMD
        /** Status Code: 404,501 etc. **/
        String STATUS_CODE = "statusCode";
        /** Status Code Error: NOT_FOUND, INTERNAL_ERROR etc. **/
        String ERROR = "error";
        /** Error Message: Exception description **/
        String ERROR_MSG = "errorMessage";
        /** SUCCESS/FAILURE/ERROR **/
        String RESPONSE = "response";
        /** 返回的错误信息Key值 **/
        String AUTH_ERROR = "authenticateError";
        /** data节点 **/
        String DATA = PARAM.DATA;
    }

    /** **/
    interface PARAM { // NOPMD
        /** **/
        String ID = "identifier"; // NOPMD
        /** **/
        String SCRIPT = "script";
        /** **/
        String DATA = "data";
        /** **/
        String SESSION = "session.id";
        /** **/
        String METHOD = "method";
        /** **/
        String FILTERS = "filters";
    }
}
