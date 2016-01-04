package com.prayer.demo.event.rr;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class PublishWorker extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("[PUBLISHER] " + Thread.currentThread().getName());
        // 1.获取EventBus
        final EventBus bus = vertx.eventBus();
        // 2.发布Message消息到Message Queue中
        final String message = "Welcome Message, Hello {0}";
        bus.publish("MSG://QUEUE/PUBLISH", message);
    }
}
