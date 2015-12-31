package com.prayer.base.model;

import com.prayer.constant.Resources;
import com.prayer.facade.kernel.JsonEntity;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMetadata implements JsonEntity {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void writeToBuffer(final Buffer buffer) {
        final String data = this.toJson().encode();
        final byte[] bytes = data.getBytes(Resources.SYS_ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
    }

    /**
     * 
     */
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        final int len = buffer.getInt(pos);
        pos += 4;
        final byte[] bytes = buffer.getBytes(pos, pos + len);
        final String data = new String(bytes, Resources.SYS_ENCODING);
        this.fromJson(new JsonObject(data));
        return pos;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
