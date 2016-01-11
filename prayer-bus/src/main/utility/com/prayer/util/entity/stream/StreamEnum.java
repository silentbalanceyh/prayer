package com.prayer.util.entity.stream;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.facade.entity.BeanGet;
import com.prayer.facade.entity.BeanSet;
import com.prayer.model.type.DataType;
import com.prayer.util.Converter;
import com.prayer.util.entity.bits.BitsString;
import com.prayer.util.string.StringKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：DataType（自定义枚举），Enum类型
 * 
 * @author Lang
 *
 */
public final class StreamEnum {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamEnum.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 将数据写入到JsonObject **/
    public static <T extends Enum<T>> void writeField(final JsonObject json, final String key, final BeanGet<T> fun) {
        final Enum<T> data = fun.get();
        if (null != data) {
            final Class<?> type = data.getClass();
            if (DataType.class == type) {
                final DataType value = (DataType) data;
                json.put(key, value.toString());
            } else {
                json.put(key, data);
            }
        }
    }

    /** 将数据从JSON中读取出来 **/
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> void readField(final JsonObject json, final String key, final BeanSet<T> fun,
            final Class<?> type) {
        final Object data = json.getValue(key);
        if (null != data) {
            T value = null;
            final Class<T> enumType = (Class<T>) type;
            if (DataType.class == type) {
                value = (T) DataType.fromString(data.toString());
            } else {
                value = Converter.fromStr(enumType, data.toString());
            }
            fun.set(value);
        }
    }

    /** 将数据写入到Buffer **/
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> void writeField(final Buffer buffer, final BeanGet<T> fun) {
        final Enum<T> data = fun.get();
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final Class<?> type = data.getClass();
            String value = null;
            if (DataType.class == type) {
                value = data.toString();
            } else {
                value = ((T) data).name();
            }
            final int length = BitsString.getLength(value);
            buffer.appendInt(length);
            bytesData = BitsString.toBytes(value);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> int readField(final Buffer buffer, int pos, final BeanSet<T> fun,
            final Class<?> type) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final String data = BitsString.fromBytes(bytesData);
            pos += length;
            T value = null;
            final Class<T> enumType = (Class<T>) type;
            if (DataType.class == type) {
                value = (T) DataType.fromString(data);
            } else {
                /** ConstraintsViolatedException **/
                if (StringKit.isNonNil(data)) {
                    value = Converter.fromStr(enumType, data);
                }
            }
            fun.set(value);
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
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
