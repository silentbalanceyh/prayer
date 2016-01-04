package com.prayer.demo.event.rr;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;

public class ReplyHandler implements Handler<AsyncResult<Message<Boolean>>> {
    private transient HttpServerResponse response;
    
    public static ReplyHandler create(final HttpServerResponse response){
        return new ReplyHandler(response);
    }

    private ReplyHandler(final HttpServerResponse response) {
        this.response = response;
    }

    @Override
    public void handle(final AsyncResult<Message<Boolean>> reply) {
        // 接受Consumer的回调函数
        if (reply.succeeded()) {
            System.out.println("[RP-HANDLER] " + Thread.currentThread().getName());
            // 获取返回值
            final boolean ret = reply.result().body();
            if (ret) {
                this.response.end(String.valueOf(ret));
            } else {
                Future.failedFuture("User Prcoessing Met Issue.");
            }
        } else {
            Future.failedFuture("Handler Internal Error");
        }
    }
}
