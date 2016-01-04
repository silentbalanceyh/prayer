package com.prayer.demo.subroute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class OneHandler implements Handler<RoutingContext> {
    @Override
    public void handle(final RoutingContext context) {
        System.out.println("[ONE] " + Thread.currentThread().getName() + ", Info: " + context.currentRoute());
        context.next();
    }
}
