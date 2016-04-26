package com.prayer.vertx.handler.standard;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class FailureHandler implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static FailureHandler create() {
        return new FailureHandler();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(RoutingContext event) {
        // TODO Auto-generated method stub
        System.out.println(getClass() + " : " + Thread.currentThread().getName());
        System.out.println(event.getBody().toString());
        event.response().end("Error");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
