package com.prayer.vertx.handler.aop.debug;

import static com.prayer.util.debug.Log.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】合并原来的Params/Header/ResponseHeader
 * @author Lang
 *
 */
public aspect AjRequestDebugger {
    /** 切点定义 **/
    pointcut ParamPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.MessageLocator.handle(RoutingContext)) && args(event) && target(Handler);
    /** 切点实现 **/
    after(final RoutingContext event):ParamPointCut(event){
        final JsonObject params = event.get(WebKeys.Request.Data.PARAMS);
        System.out.println(params.encodePrettily());
    }
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut HeaderPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.RequestAcceptor.handle(RoutingContext)) && args(event) && target(Handler);

    // ~ Point Cut Implements ================================
    /** 切点实现 **/
    before(final RoutingContext event):HeaderPointCut(event){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final MultiMap map = event.request().headers();
        final StringBuilder headers = new StringBuilder();
        map.forEach(entity -> {
            headers.append("\n" + entity.getKey() + " = " + entity.getValue());
        });
        final Logger logger = LoggerFactory.getLogger(target);
        info(logger, headers.toString());
    }
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut ErrorHeaderPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.FailureHandler.handle(RoutingContext)) && args(event) && target(Handler);

    // ~ Point Cut Implements ================================
    /** 切点实现 **/
    after(final RoutingContext event):ErrorHeaderPointCut(event){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final MultiMap map = event.response().headers();
        final StringBuilder headers = new StringBuilder();
        map.forEach(entity -> {
            headers.append("\n" + entity.getKey() + " = " + entity.getValue());
        });
        final Logger logger = LoggerFactory.getLogger(target);
        info(logger, headers.toString());
    }
}
