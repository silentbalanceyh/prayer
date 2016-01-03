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

    @Override
    public void start() {
        /**
         * 发布时调用：Deploy流程，异步多线程模式：Non-Blocked Executing
         */
        final HttpServer server = vertx.createHttpServer();
        System.out.println("[DEPLOY] " + Thread.currentThread().getName());
        
        server.requestHandler(request -> {
            /**
             * 发送请求时候调用：Request流程，单线程模式：Blocked Executing
             */
            System.out.println("[HANDLER] " + request.path() + " -> " + Thread.currentThread().getName());
            HttpServerResponse response = request.response();
            response.end("The first Vert.X Web Demo");
        });

        server.listen(8080);
    }
}
