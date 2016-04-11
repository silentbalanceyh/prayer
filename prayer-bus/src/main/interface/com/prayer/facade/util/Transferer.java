package com.prayer.facade.util;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.entity.Entity;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.json.JsonObject;

/**
 * 序列化操作
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Transferer {
    /**
     * 【Metadata】将Record转换成Entity
     * 
     * @param record
     * @param instance
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.TOOL)
    <T extends AbstractEntity<String>> T toEntity(Record record) throws AbstractDatabaseException;

    /**
     * 【Metadata】将Entity转换成Record
     * 
     * @param identifier
     * @param entity
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.TOOL)
    <T extends AbstractEntity<String>> Record fromEntity(String identifier, Entity entity)
            throws AbstractDatabaseException;

    /**
     * 【Data】用于过滤JsonObject
     * 
     * @param outJson
     * @param inJson
     */
    @VertexApi(Api.TOOL)
    void filter(JsonObject outJson, JsonObject filters);

    /**
     * 【Record】提取Record中的数据信息
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.TOOL)
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
    @VertexApi(Api.TOOL)
    Record toRecord(String identifier, Class<?> recordCls, JsonObject data) throws AbstractDatabaseException;
}
