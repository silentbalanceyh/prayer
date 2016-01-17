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
    <T> T getRule();
    /**
     * 获取当前Rule的Error信息
     * @return
     */
    JsonObject getError(String field);

    /**
     * 获取当前Rule操作的节点信息
     * 
     * @return
     */
    String position();
}
