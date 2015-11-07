package com.prayer.bus.std;

import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface MetaService {
    /**
     * POST：添加和更新Meta的接口
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> save(JsonObject jsonObject);
    /**
     * DELETE：移除Meta的接口
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> remove(JsonObject jsonObject);
    /**
     * PUT：更新Meta的接口
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> modify(JsonObject jsonObject);
    /**
     * GET：查询Meta的接口
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> find(JsonObject jsonObject);
    // ============================================================
    /**
     * POST：特殊查询接口，分页列表带排序查询
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonObject> page(JsonObject jsonObject);
}
