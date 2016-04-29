package com.prayer.vertx.handler.aop.debug;

import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Service接口的参数监控
 * @author Lang
 *
 */
public aspect AjParamsHandler {
    /** 切点定义 **/
    pointcut ParamPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.MessageLocator.handle(RoutingContext)) && args(event) && target(Handler);
    /** 切点实现 **/
    after(final RoutingContext event):ParamPointCut(event){
        final JsonObject params = event.get(WebKeys.Request.Data.PARAMS);
        System.out.println(params.encodePrettily());
    }
}
