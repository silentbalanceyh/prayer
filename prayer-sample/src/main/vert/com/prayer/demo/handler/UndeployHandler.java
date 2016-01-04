package com.prayer.demo.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public class UndeployHandler implements Handler<AsyncResult<Void>> {
    @Override
    public void handle(AsyncResult<Void> event) {
        if (event.succeeded()) {
            System.out.println("[UDP-HANDLER] Successful. Thread:" + Thread.currentThread().getName());
        } else {
            System.out.println("[UDP-HANDLER] Failure. Thread:" + Thread.currentThread().getName());
        }
    }
}
