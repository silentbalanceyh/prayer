package com.prayer.facade.schema.rule;

import java.util.List;

import com.prayer.base.exception.AbstractSchemaException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 当前Json节点的状态，用于封装读取JsonArray对象
 * 
 * @author Lang
 * 
 */
public interface ArrayHabitus {
    /**
     * 获取当前系统中所有的ObjectHabitus
     **/
    List<ObjectHabitus> objects();

    /**
     * 获取索引处的JsonObject的状态
     **/
    ObjectHabitus get(int pos);
    /**
     * 根据提供的Filer获取单个ObjectHabitus的内容
     * @param filer
     * @return
     */
    List<ObjectHabitus> get(JsonObject filter);
    /**
     * 因为JsonArray常用，所以必须获取Raw
     * @return
     */
    JsonArray data();

    /**
     * Reset 创建Raw Data的拷贝
     **/
    void reset();
    /**
     * 调用Ensure方法以保证当前ArrayHabitus初始化成功，检查Error
     * @throws AbstractSchemaException
     */
    void ensure() throws AbstractSchemaException;
}