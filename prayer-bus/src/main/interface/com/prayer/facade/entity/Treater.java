package com.prayer.facade.entity;

import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface Treater<T> {
    /**
     * 将数据写入JsonObject
     * 
     * @param json
     * @param key
     * @param fun
     */
    void writeField(final JsonObject json, final String key, final BeanGet<T> fun);

    /**
     * 将数据写入Buffer
     * 
     * @param buffer
     * @param fun
     */
    void writeField(final Buffer buffer, final BeanGet<T> fun);

    /**
     * 从Json中读取数据
     * 
     * @param json
     * @param key
     * @param fun
     */
    void readField(final JsonObject json, final String key, final BeanSet<T> fun);

    /**
     * 从Buffer中读取数据
     * 
     * @param buffer
     * @param pos
     * @param fun
     * @return
     */
    int readField(final Buffer buffer, int pos, final BeanSet<T> fun);
}
