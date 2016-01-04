package com.prayer.demo.event;

import com.prayer.constant.Resources;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;

public class User implements ClusterSerializable {
    private transient String name;

    private transient String email;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public JsonObject toJson() {
        final JsonObject message = new JsonObject();
        message.put("name", this.name);
        message.put("email", this.email);
        return message;
    }

    public User fromJson(final JsonObject message) {
        this.name = message.getString("name");
        this.email = message.getString("email");
        return this;
    }

    @Override
    public void writeToBuffer(Buffer buffer) {
        byte[] bytes = name.getBytes(Resources.SYS_ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);

        bytes = email.getBytes(Resources.SYS_ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
    }

    @Override
    public int readFromBuffer(int pos, Buffer buffer) {
        int len = buffer.getInt(pos);
        pos += 4;
        byte[] bytes = buffer.getBytes(pos, pos + len);
        name = new String(bytes, Resources.SYS_ENCODING);

        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        email = new String(bytes, Resources.SYS_ENCODING);
        return pos;
    }

}
