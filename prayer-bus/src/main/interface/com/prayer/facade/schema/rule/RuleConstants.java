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

    /** FIlters的信息 **/
    interface Filters {
        /** Filter **/
        String FILTER = "filter";
        /** Occurs **/
        String OCCURS = "occurs";
    }

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

        /** 【Array】10002 JsonType Json类型不对，但针对数组中的元素 **/
        String RULE_JETYPE = "jetype";
        /** 【Array】 Array的长度判断，最小的length必须是多少 **/
        String RULE_LEN_MIN = "minlength";
        /** 【Array】 Array的长度判断，最大的length必须是多少 **/
        String RULE_LEN_MAX = "maxlength";
        /** 【Array】10007/10008 两种Duplicated **/
        String RULE_DUP = "duplicated";
        /** 【Array】最少出现规则 **/
        String RULE_LST = "least";
        /** 【Array】最多出现规则 **/
        String RULE_MST = "most";
        /** 【Array】唯一记录规则 **/
        String RULE_UNQ = "unique";

        /** 10027 Table是否在Business数据库中存在 **/
        String RULE_DB_TB = "db.table";
        /** 10028 Column是否在Business数据库中存在 **/
        String RULE_DB_COL = "db.column";
        /** 10029 约束是否合法 **/
        String RULE_DB_CT = "db.constraint";
        /** 10030 列的类型问题 **/
        String RULE_DB_T = "db.type";
        /** 10035 检查数据库级别的Mapping验证 **/
        String RULE_DB_MAP = "db.mapping";
        /** 10036 Mapping检查 **/
        String RULE_DB_VTOR = "db.vector";
        /** 10037 Database类型改变 **/
        String RULE_DB_CHG = "db.update";
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
        /** Foreign Key Helper **/
        String CFG_FFKH = "fields/fkey/fkey-helper";
        /** Foreign Key Duplicated **/
        String CFG_FFKD = "fields/fkey/fkey-dup";

        /** Type Folder **/
        String CFG_TYPE = "type/";

        /** Keys **/
        String CFG_KEY = "keys/keys";
        /** Multi **/
        String CFG_KEY_M = "keys/multi";

        /** PrimaryKey **/
        String CFG_KPK = "keys/pk";
        /** PrimaryKey Only One **/
        String CFG_KPKO = "keys/pkone";
        /** UniqueKey **/
        String CFG_KUK = "keys/uk";
        /** ForeignKey **/
        String CFG_KFK = "keys/fk";

        /** Cross ( policy = COLLECTION ) **/
        String CFG_CPMT = "cross/policy/multit";
        /** Cross ( policy != COLLECTION ) **/
        String CFG_CPMN = "cross/policy/multin";
        /** Column **/
        String CFG_CCOL = "cross/column";
        /** Attribute **/
        String CFG_CATT = "cross/attrs";
        /** Attribute **/
        String CFG_CDBT = "cross/dbtype";
        
        /** Updates **/
        String CFG_UPS = "update/update";
    }

    /** **/
    interface Flag { // NOPMD
        /** **/
        String FLAG_IN = Constants.EMPTY_STR;
        /** **/
        String FLAG_NIN = "n't";
    }
}
