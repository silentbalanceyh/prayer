package com.prayer.bus.security;

import com.prayer.model.bus.ServiceResult;

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
	ServiceResult<JsonObject> getByName(JsonObject jsonObject);
}
