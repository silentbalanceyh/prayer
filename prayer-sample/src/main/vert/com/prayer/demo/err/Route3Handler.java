package com.prayer.demo.err;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class Route3Handler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[ROUTE3] " + Thread.currentThread().getName());
        context.next();
    }
}
