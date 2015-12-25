package com.prayer.facade.bus.console;

import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface StatusService {
    /**
     * 
     * @param params
     * @return
     */
    ServiceResult<JsonObject> getMetadata(final JsonObject params);
}
