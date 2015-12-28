package com.prayer.model.web;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface Requestor {
    /**
     * 获取数据信息
     * 
     * @return
     */
    JsonObject getData();

    /**
     * 获取Token信息
     * 
     * @return
     */
    JsonObject getToken();

    /**
     * 获取响应信息
     * 
     * @return
     */
    JsonObject getResponse();

    /**
     * 获取请求信息
     * 
     * @return
     */
    JsonObject getRequest();

    /**
     * 获取参数信息
     * 
     * @return
     */
    JsonObject getParams();
}
