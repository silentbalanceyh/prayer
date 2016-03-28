package com.prayer.facade.util;

import com.prayer.facade.entity.Entity;
import com.prayer.facade.fun.entity.Entitier;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.json.JsonObject;

/**
 * 序列化操作
 * 
 * @author Lang
 *
 */
public interface Transferer {
    /**
     * 【Metadata】将Record转换成Entity
     * 
     * @param record
     * @param instance
     * @return
     * @throws AbstractDatabaseException
     */
    <T extends AbstractEntity<String>> T toEntity(Record record, Entitier fun) throws AbstractDatabaseException;

    /**
     * 【Metadata】将Entity转换成Record
     * 
     * @param identifier
     * @param entity
     * @return
     * @throws AbstractDatabaseException
     */
    <T extends AbstractEntity<String>> Record fromEntity(String identifier, Entity entity)
            throws AbstractDatabaseException;

    /**
     * 【Data】用于过滤JsonObject
     * 
     * @param outJson
     * @param inJson
     */
    void filter(JsonObject outJson, JsonObject filters);

    /**
     * 【Record】提取Record中的数据信息
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    JsonObject fromRecord(Record record) throws AbstractDatabaseException;

    /**
     * 【Record】将JsonObject转换成Record对象
     * 
     * @param identifier
     * @param recordCls
     * @param data
     * @return
     * @throws AbstractDatabaseException
     */
    Record toRecord(String identifier, Class<?> recordCls, JsonObject data) throws AbstractDatabaseException;
}
