package com.prayer.util.entity;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.type.DataType;
import com.prayer.util.bits.BitsEnum;
import com.prayer.util.bits.BitsKit;
import com.prayer.util.bits.BitsString;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;
import com.prayer.util.reflection.Instance;

import groovyjarjarantlr.collections.List;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class EntityKit {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityKit.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 将数据写入到JsonObject **/
    public static <T> void writeField(final JsonObject json, final String key, final BeanGet<T> fun) {
        /**
         * 1.获取函数引用中的对象
         */
        final T data = fun.get();
        /**
         * 2.写入Json对象
         */
        writeObject(json, key, data);
    }

    /** 将数据从Json中读取出来 **/
    @SuppressWarnings("unchecked")
    public static <T> void readField(final JsonObject json, final String key, final BeanSet<T> fun,
            final Class<?> type) {
        /**
         * 1.从JsonObject中获取Object
         */
        final Object value = json.getValue(key);
        /**
         * 2.不为null时强制转换
         */
        if (null != value) {
            /**
             * 3.读取值的字节
             */
            if (!Instance.primitive(type)) {
                /**
                 * 4.1.非基础类型
                 */
                final T item = readObject(type, toBytes(type, (T) value));
                fun.set(item);
            } else {
                /**
                 * 4.2.基础类型
                 */
                fun.set((T) value);
            }
        }
    }

    /** 将数据写入到Buffer **/
    public static <T> void writeField(final Buffer buffer, final BeanGet<T> fun) {
        /**
         * 1.从函数中获取T对象
         */
        final T data = fun.get();
        /**
         * 2.写入这个对象的长度
         */
        writeLength(buffer, data);
        /**
         * 3.写入数据到Buffer中
         */
        writeObject(buffer, data);
    }

    /**
     * 将枚举字段从Buffer中读取出来
     * 
     * @param <E>
     **/
    public static <T extends Enum<T>> int readEnumField(final Buffer buffer, int pos, final BeanSet<T> fun,
            final Class<T> type) {
        if (DataType.class != type) {
            try {
                /**
                 * 1.根据类型获取Length
                 */
                final int length = buffer.getInt(pos);
                /**
                 * 2.跳过长度length
                 */
                pos += 4;
                /**
                 * 3.从Buffer中得到字节数组
                 */
                final byte[] bytesData = buffer.getBytes(pos, pos + length);

                /**
                 * 4.构造新的枚举类型
                 */
                final T value = BitsEnum.fromBytes(type, bytesData);
                /**
                 * 5.刷新position
                 */
                pos += length;
                if (Constants.ZERO < bytesData.length) {
                    fun.set(value);
                } else {
                    fun.set(null);
                }
            } catch (IndexOutOfBoundsException ex) {
                jvmError(LOGGER, ex);
            }
        }
        /**
         * 6.返回
         */
        return pos;
    }

    /** 将数据从Buffer中读取出来 **/
    public static <T> int readField(final Buffer buffer, int pos, final BeanSet<T> fun, final Class<?> type) {
        try {
            /**
             * 1.根据类型获取Length
             */
            final int length = buffer.getInt(pos);
            /**
             * 2.跳过长度length
             */
            pos += 4;
            /**
             * 3.从Buffer中得到字节数组
             */
            final byte[] bytesData = buffer.getBytes(pos, pos + length);
            /**
             * 4.构造读取的新的T类型
             */
            final T value = readObject(type, bytesData);
            /**
             * 5.刷新position
             */
            pos += length;
            
            if (Constants.ZERO < bytesData.length) {
                fun.set(value);
            } else {
                fun.set(null);
            }
        } catch (IndexOutOfBoundsException ex) {
            jvmError(LOGGER, ex);
        }
        /**
         * 6.返回
         */
        return pos;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static <T> void writeLength(final Buffer buffer, final T data) {
        /**
         * 1.如果为null写入0长度
         */
        if (null == data) {
            buffer.appendInt(Constants.ZERO);
        } else {
            /**
             * 2.获取T的类型
             */
            final Class<?> clazz = data.getClass();
            int length = 0;
            if (Instance.primitive(clazz)) {
                /**
                 * 3.1.基础类型
                 */
                length = BitsKit.getLength(data);
            } else {
                /**
                 * 3.2.JsonArray, JsonObject, Class, List, String, Enum
                 */
                length = BitsString.getLength(data);
            }
            buffer.appendInt(length);
        }
    }

    /**
     * 
     * @param bytesData
     * @param type
     * @return
     */
    private static <T> T readObject(final Class<?> type, final byte[] bytesData) {
        T ret = null;
        if (null != type) {
            if (Instance.primitive(type)) {
                /**
                 * 1.基础类型
                 */
                ret = BitsKit.fromBytes(type, bytesData);
            } else {
                /**
                 * 2.枚举类型单独读取：String, Class, JsonObject, JsonArray, List ( ->
                 * JsonArray )
                 */
                ret = BitsString.fromBytes(type, bytesData);
            }
        }
        return ret;
    }

    @SuppressWarnings({ "rawtypes" })
    private static <T> void writeObject(final JsonObject json, final String key, final T data) {
        if (null != data) {
            final Class<?> type = data.getClass();
            /**
             * 过滤非基础类型
             */
            if (Instance.primitive(type)) {
                /**
                 * 1.基础类型
                 */
                json.put(key, (T) data);
            } else {
                /**
                 * 2.字节数据
                 */
                final T value = readObject(type, toBytes(type, data));
                if (Class.class == type) {
                    /** Class **/
                    final String name = ((Class) value).getName();
                    json.put(key, name);
                } else if (List.class == type) {

                } else {
                    json.put(key, value);
                }
            }
        }
    }

    private static <T> void writeObject(final Buffer buffer, final T data) {
        /**
         * 1.判断type的类型
         */
        byte[] bytesData = new byte[] {};
        if (null != data) {
            final Class<?> type = data.getClass();
            bytesData = toBytes(type, data);
        }
        /**
         * 2.写入Bytes到Buffer
         */
        buffer.appendBytes(bytesData);
    }

    private static <T> byte[] toBytes(final Class<?> type, final T data) {
        byte[] bytesData = new byte[] {};
        /**
         * 1.基础类型
         */
        if (Instance.primitive(type)) {
            /**
             * 2.基础类型字节
             */
            bytesData = BitsKit.toBytes(data);
        } else if (type.isEnum()) {
            /**
             * 3.Enum类型
             */
            bytesData = BitsEnum.toBytes(data);
        } else {
            /**
             * 4.String, Class, JsonObject, JsonArray, List ( -> JsonArray )
             */
            bytesData = BitsString.toBytes(data);
        }
        return bytesData;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
