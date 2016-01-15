package com.prayer.facade.schema.rule;

import java.util.concurrent.ConcurrentMap;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 当前Json节点的状态接口，用于封装读取到的JsonObject对象
 * 
 * @author Lang
 *
 */
public interface ObjectHabitus {
    /**
     * 获取当前节点中的所有属性表，全部为String类型，因为是从JsonObject的fieldNames中提取的
     * 
     * @return
     */
    JsonArray fields();

    /**
     * 获取当前节点中所有属性的类型表
     * 
     * @return
     */
    ConcurrentMap<String, Class<?>> types();

    /**
     * 获取当前节点中所有属性的值表
     * 
     * @return
     */
    ConcurrentMap<String, Object> values();

    /**
     * 
     * @return
     */
    <T> T data();

    /**
     * 获取某个Field的值，String格式
     * 
     * @param field
     * @return
     */
    String get(String field);

    /**
     * 获取Addtional信息
     * 
     * @return
     */
    JsonObject addtional();

    /**
     * Reset，创建Raw Data的拷贝
     */
    void reset();
}
