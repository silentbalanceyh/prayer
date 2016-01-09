package com.prayer.util.bits;

import java.util.List;

import com.prayer.constant.Resources;
import com.prayer.model.type.DataType;
import com.prayer.util.reflection.Instance;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public final class BitsString {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param type
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> int getLength(final T data) {
        int len = 0;
        if (null != data) {
            final Class<?> type = data.getClass();
            if (JsonObject.class == type) {
                // JsonObject
                final JsonObject value = (JsonObject) data;
                len = value.encode().getBytes(Resources.SYS_ENCODING).length;
            } else if (JsonArray.class == type) {
                // JsonArray
                final JsonArray value = (JsonArray) data;
                len = value.encode().getBytes(Resources.SYS_ENCODING).length;
            } else if (Class.class == type) {
                // Class
                final Class<?> value = (Class<?>) data;
                len = value.getName().getBytes(Resources.SYS_ENCODING).length;
            } else if (List.class == type) {
                // List，理论上JsonArray和List的尺寸相等
                final List value = (List) data;
                final JsonArray array = extractList((List) value);
                len = array.encode().getBytes(Resources.SYS_ENCODING).length;
            } else {
                // String, Enum
                len = data.toString().getBytes(Resources.SYS_ENCODING).length;
            }
        }
        return len;
    }

    /**
     * 将一个类String转换成byte
     * 
     * @param data
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> byte[] toBytes(final T data) {
        byte[] bytes = new byte[] {};
        if (null != data) {
            if (data instanceof String) {
                // String
                bytes = ((String) data).getBytes(Resources.SYS_ENCODING);
            } else if (data instanceof Class) {
                // Class
                bytes = (((Class<?>) data).getName()).getBytes(Resources.SYS_ENCODING);
            } else if (data instanceof JsonObject) {
                // JsonObject
                bytes = (((JsonObject) data).encode()).getBytes(Resources.SYS_ENCODING);
            } else if (data instanceof JsonArray) {
                // JsonArray
                bytes = (((JsonArray) data).encode()).getBytes(Resources.SYS_ENCODING);
            } else if (data instanceof List) {
                // List -> JsonArray
                final List value = (List) data;
                final JsonArray array = extractList(value);
                bytes = (((JsonArray) array).encode()).getBytes(Resources.SYS_ENCODING);
            } else {
                // Other
                bytes = ((String) data).getBytes(Resources.SYS_ENCODING);
            }
        }
        return bytes;
    }

    /**
     * 从一个byte的数组中反转
     * 
     * @param type
     * @param bytes
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromBytes(final Class<?> type, final byte[] bytes) {
        T ret = null;
        if (null != bytes && 0 < bytes.length) {
            if (String.class == type || Class.class == type || JsonObject.class == type || JsonArray.class == type) {
                final String value = new String(bytes, Resources.SYS_ENCODING);
                if (Class.class == type) {
                    // Class
                    ret = ((T) Instance.clazz(value));
                } else if (JsonArray.class == type) {
                    // JsonObject
                    ret = (T) new JsonArray(value);
                } else if (JsonObject.class == type) {
                    // JsonArray
                    ret = (T) new JsonObject(value);
                } else if (DataType.class == type) {
                    // DataType
                    final DataType retT = DataType.fromString(value);
                    ret = (T) retT;
                } else {
                    // String
                    ret = (T) value;
                }
            }
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static <T> JsonArray extractList(final List<T> data) {
        final JsonArray retArr = new JsonArray();
        for (final T item : data) {
            retArr.add(item);
        }
        return retArr;
    }

    private BitsString() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
