package com.prayer.demo.subroute;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RouterVerticle extends AbstractVerticle {

    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOYED] " + Thread.currentThread().getName());
        Router router = Router.router(vertx);

        router.route("/path/*").order(3).handler(new Route1Handler());

        router.route("/path/*").order(2).handler(new Route2Handler());

        Router subRouter1 = Router.router(vertx);
        subRouter1.route("/sub").order(1).handler(new SubRoute1Handler("A"));
        subRouter1.route("/sub").order(2).handler(new SubRoute2Handler("A"));
        subRouter1.route("/sub").order(3).handler(new SubRoute3Handler("A"));
        router.mountSubRouter("/path", subRouter1);

        router.route("/path/sub").order(1).handler(new OneHandler());

        Router subRouter2 = Router.router(vertx);
        subRouter2.route("/sub").order(3).handler(new SubRoute1Handler("B"));
        subRouter2.route("/sub").order(2).handler(new SubRoute2Handler("B"));
        subRouter2.route("/sub").order(1).handler(new SubRoute3Handler("B"));
        router.mountSubRouter("/path", subRouter2);

        router.route("/path/sub").order(-1).handler(new NegativeOneHandler());

        router.route("/path/*").order(-2).handler(new Route3Handler());

        router.route("/path/*").last().handler(new EndHandler());

        server.requestHandler(router::accept).listen(8080);
    }

}
