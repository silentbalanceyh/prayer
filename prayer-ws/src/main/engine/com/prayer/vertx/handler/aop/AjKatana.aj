package com.prayer.vertx.handler.aop;

import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;

/**
 * 【AOP】请求拦截异常，用于生成日志信息
 * 
 * @author Lang
 *
 */
public aspect AjKatana {
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut ErrorPointCut(final JsonObject config, final String name):execution(void com.prayer.vertx.uca.katana.*Katana.interrupt(JsonObject,String)) && args(config,name);

    /** 有异常抛出 **/
    after(final JsonObject config, final String name)throwing(AbstractWebException ex):ErrorPointCut(config,name){
        final Class<?> target = thisJoinPoint.getTarget().getClass();
        final Logger logger = LoggerFactory.getLogger(target);
        peError(logger, ex);
    }
}
