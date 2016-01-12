package com.prayer.facade.schema.rule;

/**
 * 
 * @author Lang
 *
 */
public interface RuleConstants {
    /** 每一个Rule必须的Value节点value **/
    String R_VALUE = "value";

    /** Rule的名称 **/
    interface Names { // NOPMD
        /** 10001 Required **/
        String RULE_REQ = "required";
        /** 10002 JsonType **/
        String RULE_JTYPE = "jtype";
        /** 10017 Unsupported = All = Required + Supported **/
        String RULE_OPT = "optional";
        /** 10003 Pattern **/
        String RULE_PTN = "pattern";
        /** 10004 Exclude **/
        String RULE_EXD = "exclude";
        
        /** 10004 Existing **/
        String RULE_EST = "existing";
        /** 10005 In **/
        String RULE_IN = "in";
        /** 10005 Not In **/
        String RULE_NIN = "notin";
        
        
    }

    /** Rule对应的配置文件名 **/
    interface FileConfig { // NOPMD
        /** **/
        String CFG_ROOT = "__root__";
        /** **/
        String CFG_META = "__meta__";
        /** **/
        String CFG_M_REL = "__meta__-relation";
    }
}
