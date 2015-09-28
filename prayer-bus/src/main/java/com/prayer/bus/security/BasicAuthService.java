package com.prayer.bus.security;

import com.prayer.model.bus.ServiceResult;

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
