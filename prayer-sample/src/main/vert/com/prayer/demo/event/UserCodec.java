package com.prayer.demo.event;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class UserCodec implements MessageCodec<User,JsonObject>{

    @Override
    public void encodeToWire(Buffer buffer, User user) {
        user.writeToBuffer(buffer);
    }

    @Override
    public JsonObject decodeFromWire(int pos, Buffer buffer) {
        final User user = new User();
        user.readFromBuffer(pos, buffer);
        return user.toJson();
    }

    @Override
    public JsonObject transform(final User user) {
        return user.toJson();
    }

    @Override
    public String name() {
        return "UserCodec";
    }

    @Override
    public byte systemCodecID() {
        return 100;
    }

}
