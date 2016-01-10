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
    /** 全局统一属性ID信息 **/
    // TODO: 这里改动过后可能很多地方无法通过编译
    String PID = "id";

    /** OVol 默认脚本 **/
    String LANG_GROOVY = "groovy";
    /** Script Engine 名称 **/
    String SCRIPT_ENGINE = "nashorn";
    /** **/
    interface EXTENSION{  // NOPMD
        /** SQL 后缀名 **/
        String SQL = "sql";
        /** JSON 后缀名 **/
        String JSON = "json";
    }
    // ~ System ==============================================
    /** System value 2 **/
    int TWO = 2;
    /** System value 1 **/
    int ONE = 1;
    /** System value 0 **/
    int ZERO = 0;
    /** 读取Buffer的开始值 **/
    int POS = ZERO;
    /** 索引的开始值 **/
    int IDX = ZERO;
    /** 系统边界值：-1，用于很多地方的临界值使用 **/
    int RANGE = -1;
    /** HashCode base number **/
    int HASH_BASE = 31;
    /** Default StringBuilder/Buffer Size **/
    int BUFFER_SIZE = 32;
    /** Byte Array Default Buffer Size **/
    int BYTE_BUF_SIZE = 4096;
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
    /** H2的数据类型 **/
    String[] H2_TYPES = new String[] { "INT", "BOOLEAN", "TINYINT", "SMALLINT", "BIGINT", "IDENTITY", "DECIMAL",
            "DOUBLE", "REAL", "TIME", "DATE", "TIMESTAMP", "BINARY", "OTHER", "VARCHAR", "VARCHAR IGNORECASE", "CHAR",
            "BLOB", "CLOB", "UUID", "ARRAY" };

    // ~ Vertx Default =======================================
    /** HA Group Default **/
    String VX_GROUP = "__DEFAULT__";
    /** Default Convertor，默认Convertor，什么都不做 **/
    String VX_CONVERTOR = VX_GROUP;

    interface KEY { // NOPMD
        /** Global Responsor **/
        String CTX_RESPONSOR = "CTX.RESPONSOR";
        /** Global Requestor **/
        String CTX_REQUESTOR = "CTX.REQUESTOR";
        /** Final Url **/
        String CTX_FINAL_URL = "CTX.FINAL.URL";
        /** Context URI **/
        String CTX_URI = "CTX.URI";
        /** Api的远程地址 **/
        String API_URL = "API.URL";
    }

    /** **/
    interface WEB { // NOPMD
        /** **/
        String SESSION_USER = "user";
    }

    /** **/
    interface ORDER { // NOPMD
        /** 默认的顺序，但不设置，作为临界值 **/
        int NOT_SET = -20000;
        /** Failure Handler **/
        int FAILURE = 20000;

        /** 前后台共用 **/
        interface SHD { // NOPMD
            /** CORS Handler **/
            int CORS = -19900;
            /** Cookie Handler Order **/
            int COOKIE = -19800;
            /** Body Handler Order **/
            int BODY = -19700;
        }

        interface SEC { // NOPMD
            /** Authentication Handler Order **/
            int AUTH = -19600; // Web No，Engine Yes
        }

        /** Engine专用 **/
        interface ENG { // NOPMD
            /** Router Handler Order **/
            int ROUTER = -9900;
            /** Service Handler Order **/
            int SERVICE = 19000;
        }

        /** Web专用 **/
        interface WEB { // NOPMD
            /** Session Handler Order **/
            int SESSION = -10000;
            /** User Session Handler Order **/
            int USER_SESSION = -9980; // NOPMD
            /** Redirect之前的预处理 **/
            int SHARED = -9990;
            /** Logout **/
            int LOGOUT = -9970;
            /** Redirect Handler Order **/
            int ADMIN = -9960;
            /** Static Handler **/
            int STATIC = -9950;
            /** Template Handler **/
            int DYNAMIC = -9940;
            /** Context **/
            int CONTEXT = -9930;
            /** PROFILE **/
            int OD_PROFILE = -9910;
        }
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

    /**
     * 
     * @author Lang
     *
     */
    interface CMD { // NOPMD
        /** status Command **/
        interface STATUS { // NOPMD
            /** **/
            String JDBC_URL = "url";
            /** **/
            String USERNAME = "username";
            /** **/
            String PASSWORD = "password";
        }
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
        /** **/
        String ORDERS = "orders";

        /** **/
        interface PAGE { // NOPMD
            /** Page 参数 **/
            String NAME = "page";
            /** Index 参数：最小为1即表示第一页 **/
            String PAGE_INDEX = "index";
            /** Size 参数：每一页的数量 **/
            String PAGE_SIZE = "size";
            /** Return Counter **/
            String RET_COUNT = "count";
            /** Return List **/
            String RET_LIST = "list";
        }
    }

    /** **/
    interface CACHE { // NOPMD
        /** **/
        String CAC_ADDRESS = "CAC_ADDRESS";
    }

    /** **/
    interface LOCK { // NOPMD
        /** **/
        String LOK_ADDRESS = "LOCK_ADDRESS";
    }
}
