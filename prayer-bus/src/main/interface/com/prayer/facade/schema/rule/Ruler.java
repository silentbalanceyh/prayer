package com.prayer.facade.schema.rule;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 规则接口，基本规则接口
 * 
 * @author Lang
 *
 */
public interface Ruler extends RuleConstants {
    /**
     * 将某种规则用于当前的node节点，如果不合法则抛出异常，合法则返回true
     * 
     * @param node
     * @param rule
     * @return
     * @throws AbstractSchemaException
     */
    void apply(ObjectHabitus habitus) throws AbstractSchemaException;
}
