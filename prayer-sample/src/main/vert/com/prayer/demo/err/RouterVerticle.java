package com.prayer.demo.err;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RouterVerticle extends AbstractVerticle {

    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOYED] " + Thread.currentThread().getName());
        Router router = Router.router(vertx);
        router.route("/path/a").order(-1).handler(new Route1Handler());
        router.route("/path/a").order(0).failureHandler(new ErrorHandler());
        router.route("/path/*").last().handler(new EndHandler());
        server.requestHandler(router::accept).listen(8080);
    }
}
