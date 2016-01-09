package com.prayer.base.model;

import com.prayer.facade.entity.Attributes;
import com.prayer.facade.entity.Entity;
import com.prayer.util.entity.EntityKit;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;

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
    // ~ Integer Serialization Method ========================

    /** 将数据写入到JsonObject **/
    protected void writeInt(final JsonObject json, final String key, final BeanGet<Integer> fun) {
    }

    /** 将数据写入到Buffer **/
    protected void writeInt(final Buffer buffer, final BeanGet<Class<?>> fun) {
    }

    /** 从JsonObject中读取数据 **/
    protected void readInt(final JsonObject json, final String key, final BeanSet<Integer> fun) {
    }

    /** 从Buffer中读取数据 **/
    protected int readInt(int pos, final Buffer buffer, final BeanSet<Class<?>> fun) {
        return -1;
    }
    // ~ Clazz Serialization Method ==========================

    /** 将数据写入到JsonObject **/
    protected void writeClass(final JsonObject json, final String key, final BeanGet<Class<?>> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeClass(final Buffer buffer, final BeanGet<Class<?>> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readClass(final JsonObject json, final String key, final BeanSet<Class<?>> fun) {
        EntityKit.readField(json, key, fun, Class.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readClass(int pos, final Buffer buffer, final BeanSet<Class<?>> fun) {
        return EntityKit.readField(buffer, pos, fun, Class.class);
    }

    // ~ String Serialization Method =========================
    /** 将数据写入到JsonObject **/
    protected void writeString(final JsonObject json, final String key, final BeanGet<String> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeString(final Buffer buffer, final BeanGet<String> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readString(final JsonObject json, final String key, final BeanSet<String> fun) {
        EntityKit.readField(json, key, fun, String.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readString(int pos, final Buffer buffer, final BeanSet<String> fun) {
        return EntityKit.readField(buffer, pos, fun, String.class);
    }
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
