package com.prayer.demo.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RouterVerticle extends AbstractVerticle {

    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOYED] " + Thread.currentThread().getName());
        Router router = Router.router(vertx);
        router.route("/path/*").order(-1).handler(new Route1Handler());
        router.route("/path/").order(0).handler(new Route2Handler());
        router.route("/path/").order(1).handler(new Route3Handler());
        router.route("/path/*").last().handler(new EndHandler());
        server.requestHandler(router::accept).listen(8080);
    }
}
