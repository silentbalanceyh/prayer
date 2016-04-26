package com.prayer.facade.vtx.request;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public interface Alloter {
    /**
     * 提供回调函数
     * 
     * @param context
     */
    void accept(RoutingContext context, Handler<AsyncResult<JsonObject>> handler);
}
