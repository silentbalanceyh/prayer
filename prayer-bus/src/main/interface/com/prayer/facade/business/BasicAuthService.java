package com.prayer.facade.business;

import com.prayer.model.business.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface BasicAuthService {
    /**
     * 
     * @param jsonObject
     * @return
     */
    ServiceResult<JsonArray> find(JsonObject jsonObject);
}
