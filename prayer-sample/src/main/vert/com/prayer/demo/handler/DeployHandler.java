package com.prayer.demo.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public class DeployHandler implements Handler<AsyncResult<String>> {
    /**
     * 必须提供实现方法，这里的event参数等价于Lambda表达式中：res -> {}的res
     */
    @Override
    public void handle(final AsyncResult<String> event) {
        final String result = event.result();
        if (event.succeeded()) {
            System.out.println(
                    "[DP-HANDLER] Successful. Result = " + result + ",Thread:" + Thread.currentThread().getName());
        } else {
            System.out.println("[DP-HANDLER] Error occurs. Result = " + result);
        }
    }
}
