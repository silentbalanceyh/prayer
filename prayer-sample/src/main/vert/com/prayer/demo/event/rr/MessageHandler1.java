package com.prayer.demo.event.rr;

import com.prayer.demo.event.User;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

public class MessageHandler1 implements Handler<RoutingContext> {
    @Override
    public void handle(final RoutingContext context) {
        System.out.println("[MSG] " + Thread.currentThread().getName());
        final String name = context.request().getParam("name");
        final String email = context.request().getParam("email");
        // 构造User
        final User user = new User();
        user.setName(name);
        user.setEmail(email);
        // 获取Event Bus
        final EventBus bus = context.vertx().eventBus();
        // 传入DeliveryOptions来发送
        bus.<Boolean> send("MSG://QUEUE/USER", user.toJson(), reply -> {
            // 接受Consumer的回调函数
            if (reply.succeeded()) {
                System.out.println("[REPLY] " + Thread.currentThread().getName());
                // 获取返回值
                final boolean ret = reply.result().body();
                if (ret) {
                    context.response().end(String.valueOf(ret));
                } else {
                    Future.failedFuture("User Prcoessing Met Issue.");
                }
            } else {
                Future.failedFuture("Handler Internal Error");
            }
        });
    }
}
