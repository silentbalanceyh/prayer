package com.prayer.util.entity.stream;

import static com.prayer.util.debug.Log.jvmError;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.facade.entity.BeanGet;
import com.prayer.facade.entity.BeanSet;
import com.prayer.util.entity.bits.BitsString;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 处理数据：List<String>, List<JsonObject>
 * 
 * @author Lang
 *
 */
public final class StreamList {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamList.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 将数据写入到JsonObject **/
    public static <T> void writeField(final JsonObject json, final String key, final BeanGet<List<T>> fun) {
        final List<T> data = fun.get();
        if (null != data) {
            final JsonArray value = fromList(data);
            if (0 < value.size()) {
                json.put(key, value);
            }
        }
    }

    /** 将数据从JSON中读取出来 **/
    @SuppressWarnings("rawtypes")
    public static <T> void readField(final JsonObject json, final String key, final BeanSet<List> fun) {
        final Object data = json.getValue(key);
        if (null != data && data instanceof JsonArray) {
            final List<T> list = toList((JsonArray) data);
            fun.set(list);
        }
    }

    /** 将数据写入到Buffer **/
    public static <T> void writeField(final Buffer buffer, final BeanGet<List<T>> fun) {
        final List<T> data = fun.get();
        /**
         * 对于List直接写
         */
        byte[] bytesData = new byte[] {};
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
            buffer.appendBytes(bytesData);
        } else {
            final JsonArray value = fromList(data);
            final String array = value.encode();
            final int length = BitsString.getLength(array);
            buffer.appendInt(length);
            bytesData = BitsString.toBytes(array);
            buffer.appendBytes(bytesData);
        }
    }

    /** 将数据从Buffer中读取出来 **/
    @SuppressWarnings("rawtypes")
    public static <T> int readField(final Buffer buffer, int pos, final BeanSet<List> fun) {
        try {
            final int length = buffer.getInt(pos);
            pos += 4;
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            final String data = BitsString.fromBytes(bytesData);
            pos += length;
            List<T> list = new ArrayList<>();
            if (Constants.ZERO < bytesData.length) {
                final JsonArray value = new JsonArray(data);
                list = toList(value);
                fun.set(list);
            } else {
                fun.set(list);
            }
        } catch (IndexOutOfBoundsException ex) {
            jvmError(LOGGER, ex);
        } catch (DecodeException ex) {
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
    /**
     * 
     * @param data
     * @return
     */
    private static <T> JsonArray fromList(final List<T> data) {
        final JsonArray retArr = new JsonArray();
        for (final T item : data) {
            retArr.add(item);
        }
        return retArr;
    }

    /**
     * 
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> List<T> toList(final JsonArray data) {
        final List<T> list = new ArrayList<>();
        for (final Object item : data) {
            if (null != item) {
                list.add((T) item);
            }
        }
        return list;
    }

    private StreamList() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
