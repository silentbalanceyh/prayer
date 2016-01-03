package com.prayer.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

/**
 * 
 * @author Lang
 *
 */
public class RouterVerticle extends AbstractVerticle {
    /**
     * 同步启动
     */
    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOY] " + Thread.currentThread().getName());
        server.requestHandler(request -> {
            System.out.println("[HANDLER] " + request.path() + " -> " + Thread.currentThread().getName());
            HttpServerResponse response = request.response();
            response.end("The first Vert.X Web Demo");
        });

        server.listen(8080);
    }
}
