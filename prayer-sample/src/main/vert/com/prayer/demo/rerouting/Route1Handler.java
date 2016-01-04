package com.prayer.demo.rerouting;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class Route1Handler implements Handler<RoutingContext> {
    @Override
    public void handle(final RoutingContext context) {
        System.out.println("[ROUTE1] " + Thread.currentThread().getName());
        context.next();
    }
}
