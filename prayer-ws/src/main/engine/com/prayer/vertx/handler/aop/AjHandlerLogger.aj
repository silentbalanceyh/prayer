package com.prayer.vertx.handler.aop;

import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.MsgVertx;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】请求中自定义Handler的日志记录器
 * 
 * @author Lang
 *
 */
public aspect AjHandlerLogger {
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut LogPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.*.handle(RoutingContext)) && args(event) && target(Handler);

    // ~ Point Cut Implements ================================
    /** 切点实现 **/
    before(final RoutingContext event):LogPointCut(event){
        /** 1.获取每个参数信息 **/
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final String path = event.request().path();
        /** 2.读取对应日志器 **/
        final Logger logger = LoggerFactory.getLogger(target);
        info(logger, MessageFormat.format(MsgVertx.INF_HANDLER, target.getSimpleName(), path, target.getName()));
        
    }
}
