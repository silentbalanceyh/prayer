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
    /** JsonArray -> JsonObject的转换 **/
    String R_ADDT = "addtional";

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
        /** 10027 Table是否在Business数据库中存在 **/
        String RULE_DB_TB = "db.table";
        /** 10028 Column是否在Business数据库中存在 **/
        String RULE_DB_COL = "db.column";
        /** 10029 约束是否合法 **/
        String RULE_DB_CT = "db.constraint";
        /** 【Array】10007/10008 两种Duplicated **/
        String RULE_DUP = "duplicated";
        /** 【Array】最少出现规则 **/
        String RULE_LST = "least";
    }

    /** Rule对应的配置文件名 **/
    interface FileConfig { // NOPMD
        /** **/
        String CFG_ROOT = "__root__";
        /** **/
        String CFG_META = "__meta__";
        /** **/
        String CFG_FIELD = "__fields__";
        /** **/
        String CFG_FPK = "__fields__-primarykey";
        /** **/
        String CFG_M_REL = "__meta__-relation";
        /** **/
        String CFG_M_EP = "__meta__-entity-partial";
        /** **/
        String CFG_M_ED = "__meta__-entity-direct";
        /** **/
        String CFG_M_EC = "__meta__-entity-combinated";
        /** Oracle/PgSQL专用Rule **/
        String CFG_M_PIOG = "__meta__-policy-increment-og";
        /** **/
        String CFG_M_PI = "__meta__-policy-increment";
    }

    /** **/
    interface Flag { // NOPMD
        /** **/
        String FLAG_IN = Constants.EMPTY_STR;
        /** **/
        String FLAG_NIN = "n't";
    }
}
