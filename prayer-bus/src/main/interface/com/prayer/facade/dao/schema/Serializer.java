package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.exception.system.SerializationException;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEIndex;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 序列化器，用于序列化Schema
 * 
 * @author Lang
 *
 */
public interface Serializer {
    /**
     * 将一个JsonObject转换成PEMeta对象
     * 
     * @param meta
     * @return
     * @throws SerializationException
     */
    PEMeta transferMeta(final JsonObject meta) throws SerializationException;

    /**
     * 将一个JsonArray转换成List<PEKey>对象
     * 
     * @param keys
     * @return
     * @throws SerializationException
     */
    List<PEKey> transferKeys(final JsonArray keys) throws SerializationException;

    /**
     * 将一个JsonArray转换成List<PEField>对象
     * 
     * @param fields
     * @return
     * @throws SerializationException
     */
    List<PEField> transferFields(final JsonArray fields) throws SerializationException;

    /**
     * 将一个JsonArray转换成List<PEIndex>对象
     * 
     * @param indexes
     * @return
     * @throws SerializationException
     */
    List<PEIndex> transferIndexes(final JsonArray indexes) throws SerializationException;
}