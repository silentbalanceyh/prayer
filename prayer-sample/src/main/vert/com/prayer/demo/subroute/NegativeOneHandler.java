package com.prayer.demo.subroute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class NegativeOneHandler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[NEGATIVE-ONE] " + Thread.currentThread().getName() + ", Info: " + context.currentRoute());
        context.next();
    }
}
