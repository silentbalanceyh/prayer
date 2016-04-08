package com.prayer.facade.schema.rule;

import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractSchemaException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 当前Json节点的状态，用于封装读取JsonArray对象
 * 
 * @author Lang
 * 
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface ArrayHabitus {
    /**
     * 获取当前系统中所有的ObjectHabitus
     **/
    @VertexApi(Api.READ)
    List<ObjectHabitus> objects();

    /**
     * 获取索引处的JsonObject的状态
     **/
    @VertexApi(Api.READ)
    ObjectHabitus get(int pos);

    /**
     * 根据提供的Filer获取单个ObjectHabitus的内容
     * 
     * @param filer
     * @return
     */
    @VertexApi(Api.READ)
    List<ObjectHabitus> get(JsonObject filter);

    /**
     * 因为JsonArray常用，所以必须获取Raw
     * 
     * @return
     */
    @VertexApi(Api.READ)
    JsonArray data();

    /**
     * Reset 创建Raw Data的拷贝
     **/
    @VertexApi(Api.WRITE)
    void reset();

    /**
     * 调用Ensure方法以保证当前ArrayHabitus初始化成功，检查Error
     * 
     * @throws AbstractSchemaException
     */
    @VertexApi(Api.TOOL)
    void ensure() throws AbstractSchemaException;
}
