package com.prayer.demo.subroute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class Route1Handler implements Handler<RoutingContext> {
    @Override
    public void handle(final RoutingContext context) {
        System.out.println("[ROUTE1] " + Thread.currentThread().getName() + ", Info: " + context.currentRoute());
        context.next();
    }
}
