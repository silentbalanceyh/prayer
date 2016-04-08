package com.prayer.facade.entity;

import java.io.Serializable;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

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
@VertexPoint(Interface.ENG_PUBLIC)
public interface Entity extends Serializable, ClusterSerializable {
    // ~ ID相关信息 ==========================================
    /**
     * 设置Entity的ID值
     * 
     * @param id
     * @return
     */

    @VertexApi(Api.WRITE)
    void id(Serializable id);

    /**
     * 返回当前Entity的ID值
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Serializable id();

    // ~ Json序列化方法 ======================================
    /**
     * 将当前对象转换成Json
     * 
     * @return
     */
    @VertexApi(Api.TOOL)
    JsonObject toJson();

    /**
     * 从一个JsonObject得到当前的Entity对象：注意引用为当前引用
     * 
     * @param data
     * @return
     */
    @VertexApi(Api.TOOL)
    Entity fromJson(JsonObject data);
}
