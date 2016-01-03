package com.prayer.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public class RouterVerticle1 extends AbstractVerticle {

    @Override
    public void start() {
        /**
         * 发布时调用：Deploy流程，异步多线程模式：Non-Blocked Executing
         */
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOY] " + Thread.currentThread().getName());

        server.requestHandler(new DemoHandler());

        server.listen(8081);
    }
}

class DemoHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        /**
         * 发送请求时候调用：Request流程，单线程模式：Blocked Executing
         */
        System.out.println("[HANDLER] " + request.path() + " -> " + Thread.currentThread().getName());
        HttpServerResponse response = request.response();
        response.end("The first Vert.X Web Demo");
    }
}
