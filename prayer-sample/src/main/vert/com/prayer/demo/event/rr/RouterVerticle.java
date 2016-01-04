package com.prayer.demo.event.rr;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RouterVerticle extends AbstractVerticle {

    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        
        System.out.println("[DEPLOYED] " + Thread.currentThread().getName());
        
        Router router = Router.router(vertx);
        router.route("/message/:name/:email").handler(new MessageHandler());
        
        server.requestHandler(router::accept).listen(8080);
    }
}
