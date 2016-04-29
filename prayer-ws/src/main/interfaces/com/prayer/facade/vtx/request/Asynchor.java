package com.prayer.facade.vtx.request;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Asynchor {
    /**
     * 异步回调模式
     * 
     * @param context
     */
    @VertexApi(Api.TOOL)
    void accept(RoutingContext context, Handler<AsyncResult<Envelop>> handler);
}
