package com.prayer.demo.subroute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class Route3Handler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[ROUTE3] " + Thread.currentThread().getName() + ", Info: " + context.currentRoute());
        context.next();
    }
}
