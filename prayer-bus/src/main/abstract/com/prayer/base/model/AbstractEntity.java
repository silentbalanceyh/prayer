package com.prayer.base.model;

import java.util.List;

import com.prayer.facade.entity.Attributes;
import com.prayer.facade.entity.Entity;
import com.prayer.util.entity.streamer.StreamBasic;
import com.prayer.util.entity.streamer.StreamClass;
import com.prayer.util.entity.streamer.StreamEnum;
import com.prayer.util.entity.streamer.StreamJson;
import com.prayer.util.entity.streamer.StreamList;
import com.prayer.util.entity.streamer.StreamString;
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
    // ~ Enum Serialization Method ===========================
    /** 将数据写入到JsonObject **/
    protected <T extends Enum<T>> void writeEnum(final JsonObject json, final String key, final BeanGet<T> fun) {
        StreamEnum.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected <T extends Enum<T>> void writeEnum(final Buffer buffer, final BeanGet<T> fun) {
        StreamEnum.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected <T extends Enum<T>> void readEnum(final JsonObject json, final String key, final BeanSet<T> fun,
            final Class<?> type) {
        StreamEnum.readField(json, key, fun, type);
    }

    /** 从Buffer中读取数据 **/
    protected <T extends Enum<T>> int readEnum(int pos, final Buffer buffer, final BeanSet<T> fun,
            final Class<?> type) {
        return StreamEnum.readField(buffer, pos, fun, type);
    }

    // ~ List Serialization Method ===========================
    /** 将数据写入到JsonObject **/
    protected <T> void writeList(final JsonObject json, final String key, final BeanGet<List<T>> fun) {
        StreamList.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected <T> void writeList(final Buffer buffer, final BeanGet<List<T>> fun) {
        StreamList.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    @SuppressWarnings("rawtypes")
    protected <T> void readList(final JsonObject json, final String key, final BeanSet<List> fun) {
        StreamList.readField(json, key, fun);
    }

    /** 从Buffer中读取数据 **/
    @SuppressWarnings("rawtypes")
    protected <T> int readList(int pos, final Buffer buffer, final BeanSet<List> fun) {
        return StreamList.readField(buffer, pos, fun);
    }

    // ~ JsonObject Serialization Method =====================
    /** 将数据写入到JsonObject **/
    protected void writeJObject(final JsonObject json, final String key, final BeanGet<JsonObject> fun) {
        StreamJson.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeJObject(final Buffer buffer, final BeanGet<JsonObject> fun) {
        StreamJson.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readJObject(final JsonObject json, final String key, final BeanSet<JsonObject> fun) {
        StreamJson.readField(json, key, fun);
    }

    /** 从Buffer中读取数据 **/
    protected int readJObject(int pos, final Buffer buffer, final BeanSet<JsonObject> fun) {
        return StreamJson.readField(buffer, pos, fun);
    }

    // ~ Boolean Serialization Method ========================
    /** 将数据写入到JsonObject **/
    protected void writeBoolean(final JsonObject json, final String key, final BeanGet<Boolean> fun) {
        StreamBasic.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeBoolean(final Buffer buffer, final BeanGet<Boolean> fun) {
        StreamBasic.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readBoolean(final JsonObject json, final String key, final BeanSet<Boolean> fun) {
        StreamBasic.readField(json, key, fun, boolean.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readBoolean(int pos, final Buffer buffer, final BeanSet<Boolean> fun) {
        return StreamBasic.readField(buffer, pos, fun, boolean.class);
    }

    // ~ Long Serialization Method ========================
    /** 将数据写入到JsonObject **/
    protected void writeLong(final JsonObject json, final String key, final BeanGet<Long> fun) {
        StreamBasic.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeLong(final Buffer buffer, final BeanGet<Long> fun) {
        StreamBasic.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readLong(final JsonObject json, final String key, final BeanSet<Long> fun) {
        StreamBasic.readField(json, key, fun, long.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readLong(int pos, final Buffer buffer, final BeanSet<Long> fun) {
        return StreamBasic.readField(buffer, pos, fun, long.class);
    }

    // ~ Integer Serialization Method ========================
    /** 将数据写入到JsonObject **/
    protected void writeInt(final JsonObject json, final String key, final BeanGet<Integer> fun) {
        StreamBasic.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeInt(final Buffer buffer, final BeanGet<Integer> fun) {
        StreamBasic.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readInt(final JsonObject json, final String key, final BeanSet<Integer> fun) {
        StreamBasic.readField(json, key, fun, int.class);
    }

    /** 从Buffer中读取数据 **/
    protected int readInt(int pos, final Buffer buffer, final BeanSet<Integer> fun) {
        return StreamBasic.readField(buffer, pos, fun, int.class);
    }
    // ~ Clazz Serialization Method ==========================

    /** 将数据写入到JsonObject **/
    protected void writeClass(final JsonObject json, final String key, final BeanGet<Class<?>> fun) {
        StreamClass.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeClass(final Buffer buffer, final BeanGet<Class<?>> fun) {
        StreamClass.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readClass(final JsonObject json, final String key, final BeanSet<Class<?>> fun) {
        StreamClass.readField(json, key, fun);
    }

    /** 从Buffer中读取数据 **/
    protected int readClass(int pos, final Buffer buffer, final BeanSet<Class<?>> fun) {
        return StreamClass.readField(buffer, pos, fun);
    }

    // ~ String Serialization Method =========================
    /** 将数据写入到JsonObject **/
    protected void writeString(final JsonObject json, final String key, final BeanGet<String> fun) {
        StreamString.writeField(json, key, fun);
    }

    /** 将数据写入到Buffer **/
    protected void writeString(final Buffer buffer, final BeanGet<String> fun) {
        StreamString.writeField(buffer, fun);
    }

    /** 从JsonObject中读取数据 **/
    protected void readString(final JsonObject json, final String key, final BeanSet<String> fun) {
        StreamString.readField(json, key, fun);
    }

    /** 从Buffer中读取数据 **/
    protected int readString(int pos, final Buffer buffer, final BeanSet<String> fun) {
        return StreamString.readField(buffer, pos, fun);
    }
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    
}
