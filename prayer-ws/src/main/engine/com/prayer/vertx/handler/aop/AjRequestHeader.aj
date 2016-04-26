package com.prayer.vertx.handler.aop;

import static com.prayer.util.debug.Log.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】请求中的Header调试
 */
public aspect AjRequestHeader {
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
}
