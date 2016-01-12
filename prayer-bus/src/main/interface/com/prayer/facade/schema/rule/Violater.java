package com.prayer.facade.schema.rule;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public interface Violater extends RuleConstants {
    /**
     * 返回一个Schema异常表示验证不成功
     * 
     * @param habitus
     * @param rule
     * @return
     */
    AbstractSchemaException violate(ObjectHabitus habitus);
}
