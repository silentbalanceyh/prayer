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
     * Json文件 -> H2 Database
     * 
     * @param filePath
     * @return
     */
    // 1. 从JSON文件中读取Schema
    // 2. 将读取到的Schema插入到H2 Database中
    ServiceResult<Schema> importSchema(String filePath);

    /**
     * H2 Database -> SQL Database
     * 
     * @param schema
     * @return
     */
    // 1. 传入Schema同步数据从H2 Database到SQL Database中
    // 2. 同步成功过后返回同步的Schema
    ServiceResult<Schema> syncMetadata(Schema schema);

    /**
     * 从H2 Databsase中读取Schema定义信息
     * 
     * @param identifier
     * @return
     */
    // 1. 根据identifier读取Schema信息
    ServiceResult<Schema> findById(String identifier);

    /**
     * 从H2 Database中删除Schema定义信息
     * 
     * @param identifier
     * @return
     */
    // 1. 删除H2 Database的数据
    // 2. 删除数据库中的表数据
    ServiceResult<Boolean> removeById(String identifier);
}
