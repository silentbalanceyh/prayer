package com.prayer.base.model;

import java.util.List;

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
    // ~ JsonObject Serialization Method =====================
    /** 将数据写入到JsonObject **/
    @SuppressWarnings("rawtypes")
    protected void writeList(final JsonObject json, final String key, final BeanGet<List> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    @SuppressWarnings("rawtypes")
    protected void writeList(final Buffer buffer, final BeanGet<List> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    @SuppressWarnings("rawtypes")
    protected void readList(final JsonObject json, final String key, final BeanSet<List> fun) {
        EntityKit.readField(json, key, fun, List.class);
    }

    /** 从Buffer中读取数据 **/
    @SuppressWarnings("rawtypes")
    protected int readList(int pos, final Buffer buffer, final BeanSet<List> fun) {
        return EntityKit.readField(buffer, pos, fun, List.class);
    }

    // ~ JsonObject Serialization Method =====================
    /** 将数据写入到JsonObject **/
    protected void writeJObject(final JsonObject json, final String key, final BeanGet<JsonObject> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeJObject(final Buffer buffer, final BeanGet<JsonObject> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readJObject(final JsonObject json, final String key, final BeanSet<JsonObject> fun) {
        EntityKit.readField(json, key, fun, JsonObject.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readJObject(int pos, final Buffer buffer, final BeanSet<JsonObject> fun) {
        return EntityKit.readField(buffer, pos, fun, JsonObject.class);
    }

    // ~ Boolean Serialization Method ========================
    /** 将数据写入到JsonObject **/
    protected void writeBoolean(final JsonObject json, final String key, final BeanGet<Boolean> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeBoolean(final Buffer buffer, final BeanGet<Boolean> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readBoolean(final JsonObject json, final String key, final BeanSet<Boolean> fun) {
        EntityKit.readField(json, key, fun, boolean.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readBoolean(int pos, final Buffer buffer, final BeanSet<Boolean> fun) {
        return EntityKit.readField(buffer, pos, fun, boolean.class);
    }

    // ~ Integer Serialization Method ========================
    /** 将数据写入到JsonObject **/
    protected void writeInt(final JsonObject json, final String key, final BeanGet<Integer> fun) {
        EntityKit.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeInt(final Buffer buffer, final BeanGet<Integer> fun) {
        EntityKit.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readInt(final JsonObject json, final String key, final BeanSet<Integer> fun) {
        EntityKit.readField(json, key, fun, int.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readInt(int pos, final Buffer buffer, final BeanSet<Integer> fun) {
        return EntityKit.readField(buffer, pos, fun, int.class);
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
