package com.prayer.facade.business.endpoint;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

import io.vertx.core.json.JsonObject;

/**
 * Restful服务接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface ReadEndPoint {

    /**
     * GET请求专用方法
     * 
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject get(JsonObject request);

    /**
     * （POST）Page请求专用方法专用方法
     * 
     * @param request
     * @return
     */

    @VertexApi(Api.READ)
    JsonObject page(JsonObject request);
}