package com.prayer.demo.event.rr;

import com.prayer.demo.event.User;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class UserConsumer implements Handler<Message<JsonObject>> {
    
    public static UserConsumer create(){
        return new UserConsumer();
    }
    private UserConsumer(){}
    @Override
    public void handle(final Message<JsonObject> caller) {
        final JsonObject data = caller.body();
        // 3.构造User对象
        final User user = new User();
        user.fromJson(data);
        // 4.业务访问代码
        System.out.println(
                "[CALLER] Business Service: " + user.toJson() + ", Thread : " + Thread.currentThread().getName());
        // 5.执行响应
        caller.reply(Boolean.TRUE);
    }
}
