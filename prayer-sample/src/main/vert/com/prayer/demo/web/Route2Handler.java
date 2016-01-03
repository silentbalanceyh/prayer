package com.prayer.demo.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class Route2Handler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[ROUTE2] " + Thread.currentThread().getName());
        context.next();
    }
}
