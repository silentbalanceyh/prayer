package com.prayer.facade.constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 * @see All system constants which must not be changed
 */
@VertexPoint(Interface.CONSTANT)
public interface Constants { // NOPMD
    // ~ Static Fields =======================================
    /** 全局统一属性ID信息 **/
    // TODO: 这里改动过后可能很多地方无法通过编译
    String PID = "uniqueId";
    /** **/
    String TCP_PASSWORD = "db6ea8d90fa0cad8597d332cb1ee3903143588bce135c1f1100a6e62c523dd70";
    /** Script Engine 名称 **/
    String SCRIPT_ENGINE = "nashorn";

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface EXTENSION { // NOPMD
        /** INIT 后缀名 **/
        String SQL = "sql";
        /** JSON 后缀名 **/
        String JSON = "json";
        /** Properties 后缀名 **/
        String PROP = "properties";
        /** JavaScript后缀 **/
        String JS = "js";
    }

    // ~ System ==============================================
    /** System value 2 **/
    int TWO = 2;
    /** System value 1 **/
    int ONE = 1;
    /** System value 0 **/
    int ZERO = 0;
    /** 数据库影响0记录常量 **/
    int NO_ROW = ZERO;
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

    @VertexPoint(Interface.CONSTANT)
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
    @VertexPoint(Interface.CONSTANT)
    interface WEB { // NOPMD
        /** **/
        String SESSION_USER = "user";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface PARAM { // NOPMD
        /** **/
        String ID = "identifier"; // NOPMD
        /** **/
        String DATA = "data";
        /** **/
        String SESSION = "session.id";
        /** **/
        String METHOD = "method";
        /** **/
        String SCRIPT = "script";

        /** 查询根节点 **/
        String QUERY = "query";

        /** Query用的特殊参数 **/
        @VertexPoint(Interface.CONSTANT)
        interface ADMINICLE {
            /** Projection中的列信息，返回内容从这里读取 **/
            String FILTERS = "filters";
            /** Pager分页参数 **/
            String PAGER = "pager";
            /** OrderBy参数 **/
            String ORDERS = "orders";
            
            /** **/
            @VertexPoint(Interface.CONSTANT)
            interface PAGE { // NOPMD
                /** Index 参数：最小为1即表示第一页 **/
                String INDEX = "index";
                /** Size 参数：每一页的数量 **/
                String SIZE = "size";

                /** Return Counter **/
                interface RET {
                    /** Counter **/
                    String COUNT = "count";
                    /** List **/
                    String LIST = "list";
                }
            }
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface CACHE { // NOPMD
        /** **/
        String CAC_ADDRESS = "CAC_ADDRESS";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface LOCK { // NOPMD
        /** **/
        String LOK_ADDRESS = "LOCK_ADDRESS";
    }
}
