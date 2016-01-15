package com.prayer.facade.schema.rule;

import com.prayer.constant.Constants;

/**
 * 
 * @author Lang
 *
 */
public interface RuleConstants {
    /** 每一个Rule必须的Value节点value **/
    String R_VALUE = "value";
    /** JsonArray -> JsonObject的转换 **/
    String R_DATA = "data";
    /** **/
    String R_RULE = "rule";
    /** JsonArray -> JsonObject的转换 **/
    String R_ADDT = "addtional";
    /** Error **/
    String R_ERROR = "error";
    /** Error Group **/
    String R_ERRORS = "errors";

    /** Rule的名称 **/
    interface Names { // NOPMD
        /** 10001 Required 必要属性丢失 **/
        String RULE_REQ = "required";
        /** 10002 JsonType Json类型不对，必须是JsonObject或JsonArray **/
        String RULE_JTYPE = "jtype";
        /** 10017 Unsupported = All - Required - Optional 出现了不支持的属性 **/
        String RULE_OPT = "optional";
        /** 10003 Pattern 正则表达式，存在属性值不符合 **/
        String RULE_PTN = "pattern";
        /** 10004 Exclude 某些属性不可以出现 **/
        String RULE_EXD = "exclude";
        /** 10004 Existing 属性必须同时出现 **/
        String RULE_EST = "existing";
        /** 10005 In 属性的值必须在某些合法值中 **/
        String RULE_IN = "in";
        /** 10005 Not In 属性的值不能在某些非合法值中 **/
        String RULE_NIN = "notin";
        /** 10020 两个属性值不可相同 **/
        String RULE_DIFF = "diff";

        /** 【Array】10007/10008 两种Duplicated **/
        String RULE_DUP = "duplicated";
        /** 【Array】最少出现规则 **/
        String RULE_LST = "least";
        /** 【Array】最多出现规则 **/
        String RULE_MST = "most";
        
        /** 10027 Table是否在Business数据库中存在 **/
        String RULE_DB_TB = "db.table";
        /** 10028 Column是否在Business数据库中存在 **/
        String RULE_DB_COL = "db.column";
        /** 10029 约束是否合法 **/
        String RULE_DB_CT = "db.constraint";
        /** 10030 列的类型问题 **/
        String RULE_DB_T = "db.type";
    }

    /** Rule对应的配置文件名 **/
    interface FileConfig { // NOPMD
        /** Root节点配置 **/
        String CFG_ROOT = "root/data";
        
        // ~ Meta =============================================
        /** Meta定义部分 **/
        String CFG_META = "meta/meta";
        
        /** Category = RELATION **/
        String CFG_M_REL = "meta/relation-data";
        
        /** Category = ENTITY, Mapping = COMBINATED **/
        String CFG_M_EC = "meta/mapping/combinated";
        /** Category = ENTITY, Mapping = DIRECT **/
        String CFG_M_ED = "meta/mapping/direct";
        /** Category = ENTITY, Mapping = PARTIAL **/
        String CFG_M_EP = "meta/mapping/partial";
        
        /** Policy = INCREMENT, seqnam必须，Oracle/PgSQL常用Rule **/
        String CFG_M_PIOG = "meta/increment/sequence";
        /** Policy = INCREMENT, seqname非必须，MySQL/SQLServer类型常用Rule **/
        String CFG_M_PI = "meta/increment/identity";
        
        // ~ Fields ===========================================
        /** Field定义部分 **/
        String CFG_FIELD = "fields/fields";

        /** Primary Key 基本验证 **/
        String CFG_FPK = "fields/pkey/pkey";
        /** COLLECTION类型验证 **/
        String CFG_FPK_COL = "fields/pkey/col";
        /** 非COLLECTION类型验证 **/
        String CFG_FPK_NCOL = "fields/pkey/ncol";

        /** PK Increment **/
        String CFG_FPI = "fields/pkey/policy/increment";
        /** PK GUID **/
        String CFG_FPG = "fields/pkey/policy/guid";
        /** PK Collection **/
        String CFG_FPC = "fields/pkey/policy/collection";
        /** PK Assigned **/
        String CFG_FPA = "fields/pkey/policy/assigned";
        
        /** SubRel **/
        String CFG_SUB = "fields/subs/subs";
        
        /** Foreign Key **/
        String CFG_FFK = "fields/fkey/fkey";
    }

    /** **/
    interface Flag { // NOPMD
        /** **/
        String FLAG_IN = Constants.EMPTY_STR;
        /** **/
        String FLAG_NIN = "n't";
    }
}
