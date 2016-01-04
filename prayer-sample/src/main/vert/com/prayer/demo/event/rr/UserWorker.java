package com.prayer.demo.event.rr;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class UserWorker extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("[WORKER] " + Thread.currentThread().getName());
        // 1.获取EventBus
        final EventBus bus = vertx.eventBus();
        // 2.读取Message
        bus.<JsonObject> consumer("MSG://QUEUE/USER",UserConsumer.create());
    }
}
