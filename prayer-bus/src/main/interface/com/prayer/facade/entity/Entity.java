package com.prayer.facade.entity;

import java.io.Serializable;

import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;

/**
 * 
 * @author Lang
 *
 */
// 1.实现Serializable序列化接口
// 2.实现ClusterSerializable接口，用于Vert.X中的SharedData
// 3.实现EntityAssistant接口，包含了不常用的Internal辅助方法
public interface Entity extends Serializable, ClusterSerializable {
    
    
    /**
     * 将当前对象转换成Json
     * 
     * @return
     */
    JsonObject toJson();

    /**
     * 从一个JsonObject得到当前的Entity对象：注意引用为当前引用
     * 
     * @param data
     * @return
     */
    Entity fromJson(JsonObject data);
}
