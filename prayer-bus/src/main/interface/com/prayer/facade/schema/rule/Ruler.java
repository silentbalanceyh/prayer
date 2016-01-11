package com.prayer.facade.schema.rule;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 规则接口，基本规则接口
 * @author Lang
 *
 */
public interface Ruler {
    /**
     * 将某种规则用于当前的node节点，如果不合法则抛出异常，合法则返回true
     * @param node
     * @param rule
     * @return
     * @throws AbstractSchemaException
     */
    boolean apply(ObjectHabitus habitus, Rule rule) throws AbstractSchemaException;
}
