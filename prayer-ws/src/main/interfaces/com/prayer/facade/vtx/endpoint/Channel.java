package com.prayer.facade.vtx.endpoint;

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
@VertexPoint(Interface.INTERNAL)
public interface Channel {
    /**
     * 连接业务逻辑接口
     * 
     * @param data
     * @return
     */
    @VertexApi(Api.TOOL)
    ActResponse invoke(final JsonObject data);
}
