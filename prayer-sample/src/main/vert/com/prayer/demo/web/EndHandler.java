package com.prayer.demo.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class EndHandler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[END] " + Thread.currentThread().getName());

        context.response().end("End Route");
    }
}
