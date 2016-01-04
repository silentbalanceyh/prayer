package com.prayer.demo.err;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class ErrorHandler implements Handler<RoutingContext>{
    @Override
    public void handle(final RoutingContext context){
        System.out.println("[ERROR] " + Thread.currentThread().getName());
        System.out.println("Error = " + context.statusCode());
        HttpServerResponse response = context.response();
        response.setStatusCode(context.statusCode()).end("Error Occurs");
    }
}
