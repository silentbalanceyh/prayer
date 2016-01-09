package com.prayer.util.entity.streamer;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.util.entity.bits.BitsKit;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：Boolean, Character, Integer, Short, Long, Double, Float
 * 
 * @author Lang
 *
 */
public final class StreamBasic {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamBasic.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 将数据写入到JsonObject **/
    public static <T> void writeField(final JsonObject json, final String key, final BeanGet<T> fun) {
        final T data = fun.get();
        if (null != data) {
            json.put(key, (T) data);
        }
    }

    /** 将数据从Json中读取出来 **/
    @SuppressWarnings("unchecked")
    public static <T> void readField(final JsonObject json, final String key, final BeanSet<T> fun,
            final Class<?> type) {
        final Object value = json.getValue(key);
        if (null != value) {
            fun.set((T) value);
        }
    }

    /** 将数据写入到Buffer **/
    public static <T> void writeField(final Buffer buffer, final BeanGet<T> fun) {
        final T data = fun.get();
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final int length = BitsKit.getLength(data);
            buffer.appendInt(length);
            bytesData = BitsKit.toBytes(data);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    public static <T> int readField(final Buffer buffer, int pos, final BeanSet<T> fun, final Class<?> type) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final T value = BitsKit.fromBytes(type, bytesData);
            pos += length;
            if (Constants.ZERO < bytesData.length) {
                fun.set(value);
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
    private StreamBasic() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
