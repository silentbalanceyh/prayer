package com.prayer.facade.schema.rule;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 规则接口，基本Array规则
 * @author Lang
 *
 */
public interface ArrayRuler extends RuleConstants{
    /**
     * 
     * @param habitus
     * @throws AbstractSchemaException
     */
    void apply(ArrayHabitus habitus) throws AbstractSchemaException;
}
