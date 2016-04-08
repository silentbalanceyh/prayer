package com.prayer.util.entity.stream;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.fun.entity.BeanGet;
import com.prayer.facade.fun.entity.BeanSet;
import com.prayer.util.entity.bits.BitsString;
import com.prayer.util.reflection.Instance;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：Class，将类名转换成String
 * 
 * @author Lang
 *
 */
public final class StreamClass {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamClass.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 将数据写入到JsonObject **/
    public static void writeField(final JsonObject json, final String key, final BeanGet<Class<?>> fun) {
        final Class<?> data = fun.get();
        if (null != data) {
            json.put(key, data.getName());
        }
    }

    /** 将数据从JSON中读取出来 **/
    public static void readField(final JsonObject json, final String key, final BeanSet<Class<?>> fun) {
        final Object data = json.getValue(key);
        if (null != data && data instanceof String) {
            final Class<?> value = Instance.clazz(data.toString());
            fun.set(value);
        }
    }

    /** 将数据写入到Buffer **/
    public static void writeField(final Buffer buffer, final BeanGet<Class<?>> fun) {
        final Class<?> data = fun.get();
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final String name = data.getName();
            final int length = BitsString.getLength(name);
            buffer.appendInt(length);
            bytesData = BitsString.toBytes(name);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    public static int readField(final Buffer buffer, int pos, final BeanSet<Class<?>> fun) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final String data = BitsString.fromBytes(bytesData);
            pos += length;
            if (Constants.ZERO < bytesData.length) {
                final Class<?> value = Instance.clazz(data);
                fun.set(value);
            } else {
                fun.set(null);
            }
        } catch (IndexOutOfBoundsException ex) {
            jvmError(LOGGER, ex);
        }
        return pos;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private StreamClass() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
