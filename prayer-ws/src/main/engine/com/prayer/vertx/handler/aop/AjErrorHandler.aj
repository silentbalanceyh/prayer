package com.prayer.vertx.handler.aop;

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
    pointcut ErrorPointCut(final RoutingContext event):execution(void com.prayer.vertx.handler.standard.*.handle(RoutingContext)) && args(event);

    /** 抛出异常 **/
    after(final RoutingContext event)throwing(Throwable ex):ErrorPointCut(event){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final Logger logger = LoggerFactory.getLogger(target);
        jvmError(logger, ex);
        ex.printStackTrace();
    }
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
