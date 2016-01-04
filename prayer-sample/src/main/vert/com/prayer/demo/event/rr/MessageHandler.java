package com.prayer.demo.event.rr;

import com.prayer.demo.event.User;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

public class MessageHandler implements Handler<RoutingContext> {
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
        bus.<Boolean> send("MSG://QUEUE/USER", user.toJson(), ReplyHandler.create(context.response()));
    }
}
