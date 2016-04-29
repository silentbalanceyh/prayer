package com.prayer.vertx.handler.executor;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Engine执行器
 * 
 * @author Lang
 *
 */
public class EngineExecutor implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(RoutingContext event) {
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
