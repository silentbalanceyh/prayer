package com.prayer.facade.schema.rule;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

import io.vertx.core.json.JsonObject;

/**
 * 基本规则接口，定义子元级别的规则信息
 * 
 * @author Lang
 * 
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Rule extends RuleConstants {
    /**
     * 获取当前Rule的信息
     */
    @VertexApi(Api.READ)
    <T> T getRule();
    /**
     * 获取当前Rule的Error信息
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject getError(String field);

    /**
     * 获取当前Rule操作的节点信息
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String position();
}
