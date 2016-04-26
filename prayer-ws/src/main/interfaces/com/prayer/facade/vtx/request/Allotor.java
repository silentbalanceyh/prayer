package com.prayer.facade.vtx.request;

import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public interface Allotor {
    /**
     * 同步无回调模式
     * @param context
     * @return
     */
    Envelop accept(RoutingContext context,JsonObject params);
}
