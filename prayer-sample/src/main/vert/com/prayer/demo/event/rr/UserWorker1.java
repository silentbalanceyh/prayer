package com.prayer.demo.event.rr;

import java.text.MessageFormat;

import com.prayer.demo.event.User;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class UserWorker1 extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("[WORKER] " + Thread.currentThread().getName());
        // 1.获取EventBus
        final EventBus bus = vertx.eventBus();
        // 2.读取Message
        bus.<JsonObject> consumer("MSG://QUEUE/USER", caller -> {
            final JsonObject data = caller.body();

            final User user = new User();
            user.fromJson(data);
            // 4.业务访问代码
            System.out.println("[CALLER] Business Service: " + user.toJson() + ", Thread : " + Thread.currentThread().getName());
            // 5.执行响应
            caller.reply(Boolean.TRUE);
        });
        // 3.读取Publish消息
        bus.<String> consumer("MSG://QUEUE/PUBLISH", innerCaller -> {
            final String pattern = innerCaller.body();
            final Class<?> clazz = getClass();
            final String message = MessageFormat.format(pattern, clazz.getName());
            System.out.println(message);
        });
    }
}
