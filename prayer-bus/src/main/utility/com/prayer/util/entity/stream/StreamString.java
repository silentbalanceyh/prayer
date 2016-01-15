package com.prayer.util.entity.stream;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.facade.fun.entity.BeanGet;
import com.prayer.facade.fun.entity.BeanSet;
import com.prayer.util.entity.bits.BitsString;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：String
 * 
 * @author Lang
 *
 */
public final class StreamString {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamString.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 将数据写入到JsonObject **/
    public static void writeField(final JsonObject json, final String key, final BeanGet<String> fun) {
        final String data = fun.get();
        if (null != data) {
            json.put(key, data);
        }
    }

    /** 将数据从JSON中读取出来 **/
    public static void readField(final JsonObject json, final String key, final BeanSet<String> fun) {
        final Object data = json.getValue(key);
        if (null != data && data instanceof String) {
            final String value = json.getString(key);
            fun.set(value);
        }
    }

    /** 将数据写入到Buffer **/
    public static void writeField(final Buffer buffer, final BeanGet<String> fun) {
        final String data = fun.get();
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final int length = BitsString.getLength(data);
            buffer.appendInt(length);
            bytesData = BitsString.toBytes(data);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    public static int readField(final Buffer buffer, int pos, final BeanSet<String> fun) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final String data = BitsString.fromBytes(bytesData);
            pos += length;
            if (0 < bytesData.length) {
                fun.set(data);
            } else {
                fun.set(null);
            }
        } catch (IndexOutOfBoundsException ex) {
            jvmError(LOGGER, ex);
        }
        return pos;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private StreamString() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
