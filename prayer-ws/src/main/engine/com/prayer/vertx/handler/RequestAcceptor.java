package com.prayer.vertx.handler;

import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class RequestAcceptor implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static RequestAcceptor create() {
        return new RequestAcceptor();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(final RoutingContext event) {
        // TODO Auto-generated method stub
        final String uri = event.get(WebKeys.Request.URI);
        System.out.println(uri);
        System.out.println(getClass() + " : " + Thread.currentThread().getName());
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
