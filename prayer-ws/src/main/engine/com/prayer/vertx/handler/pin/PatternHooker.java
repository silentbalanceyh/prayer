package com.prayer.vertx.handler.pin;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public class PatternHooker implements Handler<RoutingContext>{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 占位用的Handler，无实际意义，主要用于Pattern钩子 **/
    @Override
    public void handle(final RoutingContext event) {
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
