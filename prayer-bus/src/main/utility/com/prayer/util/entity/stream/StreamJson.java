package com.prayer.util.entity.stream;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.fun.entity.BeanGet;
import com.prayer.facade.fun.entity.BeanSet;
import com.prayer.util.entity.bits.BitsString;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：JsonObject
 * 
 * @author Lang
 *
 */
public final class StreamJson {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamJson.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 将数据写入到JsonObject **/
    public static void writeField(final JsonObject json, final String key, final BeanGet<JsonObject> fun) {
        final JsonObject data = fun.get();
        if (null != data) {
            json.put(key, data);
        }
    }

    /** 将数据从JSON中读取出来 **/
    public static void readField(final JsonObject json, final String key, final BeanSet<JsonObject> fun) {
        final Object data = json.getValue(key);
        if (null != data && data instanceof JsonObject) {
            final JsonObject value = json.getJsonObject(key);
            fun.set(value);
        }
    }

    /** 将数据写入到Buffer **/
    public static void writeField(final Buffer buffer, final BeanGet<JsonObject> fun) {
        final JsonObject data = fun.get();
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final String name = data.encode();
            final int length = BitsString.getLength(name);
            buffer.appendInt(length);
            bytesData = BitsString.toBytes(name);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    public static int readField(final Buffer buffer, int pos, final BeanSet<JsonObject> fun) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final String data = BitsString.fromBytes(bytesData);
            pos += length;
            final JsonObject value = new JsonObject();
            if (Constants.ZERO < bytesData.length) {
                value.mergeIn(new JsonObject(data));
                fun.set(value);
            }
        } catch (IndexOutOfBoundsException ex) {
            jvmError(LOGGER, ex);
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
        }
        return pos;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private StreamJson() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
