package com.prayer.facade.schema.rule;

import io.vertx.core.json.JsonObject;

/**
 * 基本规则接口，定义子元级别的规则信息
 * 
 * @author Lang
 * 
 */
public interface Rule extends RuleConstants {
    /**
     * 获取当前Rule的信息
     */
    JsonObject getRule();
}
