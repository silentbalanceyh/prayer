package com.prayer.facade.business;

import com.prayer.model.business.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Record的接口
 * @author Lang
 *
 */
public interface RecordService {
    /**
     * POST : 添加和更新Record的接口，参数为请求数据
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> save(JsonObject jsonObject);
    /**
     * DELETE : 移除Record的接口，参数为请求数据
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> remove(JsonObject jsonObject);
    /**
     * PUT : 更新Record的接口
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> modify(JsonObject jsonObject);
    /**
     * GET : 查询Record的接口，参数为请求数据
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonArray> find(JsonObject jsonObject);
    // ===========================================================
    /**
     * POST：特殊查询接口，分页列表带排序查询
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> page(JsonObject jsonObject);
}
