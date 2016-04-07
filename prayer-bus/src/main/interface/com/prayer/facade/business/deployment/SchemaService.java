package com.prayer.facade.business.deployment;

import com.prayer.facade.schema.Schema;
import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaService {
    /**
     * Json文件 -> Metadata Database
     * 
     * @param filePath
     * @return
     */
    // 1. 从JSON文件中读取Schema
    // 2. 将读取到的Schema插入到Metadata Database中
    ServiceResult<Schema> importSchema(String filePath);

    /**
     * Metadata Database -> SQL Database
     * 
     * @param schema
     * @return
     */
    // 1. 传入Schema同步数据从Metadata Database到SQL Database中
    // 2. 同步成功过后返回同步的Schema
    ServiceResult<Schema> syncMetadata(Schema schema);

    /**
     * 从Metadata Databsase中读取Schema定义信息
     * 
     * @param identifier
     * @return
     */
    // 1. 根据identifier读取Schema信息
    ServiceResult<Schema> findById(String identifier);

    /**
     * 从Metadata Database中删除Schema定义信息
     * 
     * @param identifier
     * @return
     */
    // 1. 删除Metadata Database的数据
    // 2. 删除数据库中的表数据
    ServiceResult<Boolean> removeById(String identifier);

    /**
     * 从Metadata Database中读取所有的表集
     * 
     * @return 返回从Metadata Database中删除的业务数据库表集
     */
    ServiceResult<Boolean> purge();
}
