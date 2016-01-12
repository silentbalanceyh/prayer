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
    }

    /** Rule对应的配置文件名 **/
    interface FileConfig { // NOPMD
        /** **/
        String CFG_ROOT = "__root__";
    }
}
