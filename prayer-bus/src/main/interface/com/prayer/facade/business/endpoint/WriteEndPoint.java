package com.prayer.facade.business.endpoint;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.business.behavior.ActResponse;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.RESTFUL)
public interface WriteEndPoint {
    /**
     * PUT请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    ActResponse put(JsonObject request);
    /**
     * POST请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    ActResponse post(JsonObject request);
    /**
     * DELETE请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    ActResponse delete(JsonObject request);
}
