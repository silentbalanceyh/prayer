package com.prayer.base.model;

import com.prayer.facade.entity.Attributes;
import com.prayer.facade.entity.Entity;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;
import com.prayer.util.model.EntityKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractEntity implements Entity, Attributes {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 5392582760585717949L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================

    // ~ Clazz Serialization Method ==========================

    /**
     * 在JsonObject设置值
     * 
     * @param json
     * @param key
     * @param value
     */
    protected void writeClass(final JsonObject json, final String key, final BeanGet<Class<?>> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /**
     * 将String类型的数据写入到Buffer中
     * 
     * @param buffer
     * @param data
     */
    protected void writeClass(final Buffer buffer, final BeanGet<Class<?>> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /**
     * 
     * @param json
     * @param key
     * @param fun
     */
    protected void readClass(final JsonObject json, final String key, final BeanSet<Class<?>> fun) {
        EntityKit.readField(json, key, fun, Class.class);
    }

    /**
     * 从当前Buffer中获取数据，并且调用函数设置字段
     * 
     * @param buffer
     * @param fun
     * @return
     */
    protected int readClass(int pos, final Buffer buffer, final BeanSet<Class<?>> fun) {
        return EntityKit.readField(pos, buffer, fun, Class.class);
    }

    // ~ String Serialization Method =========================
    /**
     * 在JsonObject设置值
     * 
     * @param json
     * @param key
     * @param value
     */
    protected void writeString(final JsonObject json, final String key, final BeanGet<String> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /**
     * 将String类型的数据写入到Buffer中
     * 
     * @param buffer
     * @param data
     */
    protected void writeString(final Buffer buffer, final BeanGet<String> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /**
     * 
     * @param json
     * @param key
     * @param fun
     */
    protected void readString(final JsonObject json, final String key, final BeanSet<String> fun) {
        EntityKit.readField(json, key, fun, String.class);
    }

    /**
     * 从当前Buffer中获取数据，并且调用函数设置字段
     * 
     * @param buffer
     * @param fun
     * @return
     */
    protected int readString(int pos, final Buffer buffer, final BeanSet<String> fun) {
        return EntityKit.readField(pos, buffer, fun, String.class);
    }
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
