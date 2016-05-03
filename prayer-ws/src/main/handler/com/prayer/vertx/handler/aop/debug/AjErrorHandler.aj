package com.prayer.vertx.handler.aop.debug;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】请求拦截异常信息，主要用于Debug
 * 
 * @author Lang
 *
 */
public aspect AjErrorHandler {
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut NonSecureErrorPointCut(final RoutingContext event):execution(void com.prayer.vertx.handler.standard.*.handle(RoutingContext)) && args(event);

    /** 抛出异常 **/
    after(final RoutingContext event)throwing(Throwable ex):NonSecureErrorPointCut(event){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final Logger logger = LoggerFactory.getLogger(target);
        jvmError(logger, ex);
        ex.printStackTrace();
    }
    
    /** 切点定义 **/
    pointcut SecureErrorPointCut(final RoutingContext event):execution(void com.prayer.secure.handler.AuthorizeKeaper.handle(RoutingContext)) && args(event);

    /** 抛出异常 **/
    after(final RoutingContext event)throwing(Throwable ex):SecureErrorPointCut(event){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final Logger logger = LoggerFactory.getLogger(target);
        jvmError(logger, ex);
        ex.printStackTrace();
    }

}
