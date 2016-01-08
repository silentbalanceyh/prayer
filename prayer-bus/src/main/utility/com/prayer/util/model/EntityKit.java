package com.prayer.util.model;

import static com.prayer.util.Instance.clazz;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class EntityKit {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Read Methods ========================================
    /**
     * 从Json中读取属性信息到对象
     * 
     * @param json
     * @param key
     * @param fun
     * @param type
     */
    public static <T> void readField(final JsonObject json, final String key, final BeanSet<T> fun,
            final Class<?> type) {
        // 1.调用Json的Get方法
        final Object data = json.getValue(key);
        // 2.如果不为空就直接强转
        final T value = fromObject(data, type);
        // 3.调用设置获取函数
        fun.set(value);
    }

    /**
     * 读Buffer
     * 
     * @param pos
     * @param buffer
     * @param fun
     * @return
     */
    public static <T> int readField(int pos, final Buffer buffer, final BeanSet<T> fun, final Class<?> type) {
        // 1.获取字节数组长度
        final int length = buffer.getInt(pos);
        // 2.跳过length长度部分
        pos += 4;
        // 3.从Buffer中读取bytesData
        final byte[] bytesData = buffer.getBytes(pos, pos + length);
        // 4.构造读取新的T信息
        final T value = fromBytes(type, bytesData);
        // 5.刷新position
        pos += length;
        if (Constants.ZERO < length) {
            // 6.调用set函数
            fun.set(value);
        } else {
            // 6.读取的为0，则序列化成null
            fun.set(null);
        }
        // 7.返回最终的jBuffer位置
        return pos;
    }

    // ~ Write Methods =======================================
    /**
     * 将字段值写入到JsonObject中
     * 
     * @param data
     * @param key
     * @param fun
     */
    public static <T> void writeField(final JsonObject json, final String key, final BeanGet<T> fun) {
        // 1.调用Get方法
        final T data = fun.get();
        // 2.如果为null引用则不填充
        if (null != data) {
            json.put(key, data);
        }
    }

    /**
     * 写Buffer
     * 
     * @param buffer
     * @param fun
     */
    public static <T> void writeField(final Buffer buffer, final BeanGet<T> fun) {
        // 1.调用函数引用的get方法
        final T data = fun.get();
        // 2.从Optional对象中获取bytes
        final byte[] bytesData = toBytes(data);
        if (null != bytesData) {
            // 3.先在Buffer中添加对象的字节数组长度length
            buffer.appendInt(bytesData.length);
            // 4.将对象转换成Bytes添加到Buffer中
            buffer.appendBytes(bytesData);
        } else {
            // 3.为空的话长度写0
            buffer.appendInt(Constants.ZERO);
            // 4.写入空字符串
            buffer.appendBytes(Constants.EMPTY_STR.getBytes(Resources.SYS_ENCODING));
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static <T> T fromObject(final Object value, final Class<?> type) {
        T ret = null;
        if (null != value) {
            ret = fromString(type, value.toString());
        }
        return ret;
    }

    /**
     * 反转
     * 
     * @param instance
     * @param bytesData
     * @return
     */
    private static <T> T fromBytes(final Class<?> type, final byte[] bytesData) {
        T ret = null;
        if (null != bytesData) {
            final String data = new String(bytesData, Resources.SYS_ENCODING);
            ret = fromString(type, data);
        }
        return ret;
    }

    /**
     * 反转
     * 
     * @param type
     * @param data
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> T fromString(final Class type, final String data) {
        T ret = null;
        if (null != data) {
            if (Class.class == type) {
                ret = (T) clazz(data);
            } else if (Enum.class == type) {
                ret = (T) Enum.valueOf(type, data);
            } else if (String.class == type) {
                ret = (T) data;
            }
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return
     */
    private static <T> String toString(final T value) {
        String retStr = null;
        if (null != value) {
            final Class<?> type = value.getClass();
            if (Class.class == type) {
                retStr = ((Class<?>) value).getName();
            } else {
                retStr = value.toString();
            }
        }
        return retStr;
    }

    /**
     * 自定义泛型转换，因为两次抽象，所以这里不能直接使用T，而且使用了JDK 8.0中的Optional<T>防止Null Pointer
     * 
     * @param instance
     * @return
     */
    private static <T> byte[] toBytes(final T instance) {
        final String literal = toString(instance);
        byte[] bytes = null;
        if (null != literal) {
            bytes = literal.getBytes(Resources.SYS_ENCODING);
        }
        return bytes;
    }

    private EntityKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
