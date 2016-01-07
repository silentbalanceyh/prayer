package com.prayer.uca;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
/**
 * 用于WebSender
 * @author Lang
 *
 */
public interface WebSender extends Handler<AsyncResult<Message<Object>>>{
    /**
     * 设置HttpServerResponse
     * @param response
     */
    void injectResponse(HttpServerResponse response);
}
