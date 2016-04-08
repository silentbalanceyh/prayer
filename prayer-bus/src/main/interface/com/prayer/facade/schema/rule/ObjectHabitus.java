package com.prayer.facade.schema.rule;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 当前Json节点的状态接口，用于封装读取到的JsonObject对象
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface ObjectHabitus {
    /**
     * 获取当前节点中的所有属性表，全部为String类型，因为是从JsonObject的fieldNames中提取的
     * 
     * @return
     */
    @VertexApi(Api.READ)
    JsonArray fields();

    /**
     * 获取当前节点中所有属性的类型表
     * 
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String, Class<?>> types();

    /**
     * 获取当前节点中所有属性的值表
     * 
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String, Object> values();

    /**
     * 
     * @return
     */
    @VertexApi(Api.READ)
    <T> T data();

    /**
     * 获取某个Field的值
     * 
     * @param field
     * @return
     */
    @VertexApi(Api.READ)
    Object get(String field);

    /**
     * 获取Addtional信息
     * 
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject addtional();

    /**
     * 获取Object额外的filter
     * 
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject filter();

    /**
     * 设置Filter的信息
     * 
     * @param filter
     */
    @VertexApi(Api.TOOL)
    void filter(final JsonObject filter);

    /**
     * Reset，创建Raw Data的拷贝
     */
    @VertexApi(Api.WRITE)
    void reset();
}
