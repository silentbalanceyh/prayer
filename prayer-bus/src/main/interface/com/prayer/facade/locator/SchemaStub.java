package com.prayer.facade.locator;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaStub {
    /**
     * 
     * @param request
     * @return
     */
    JsonObject importSchema(JsonObject request);

    /**
     * 
     * @param request
     * @return
     */
    JsonObject syncMetadata(JsonObject request);

    /**
     * 
     * @param request
     * @return
     */
    JsonObject findById(JsonObject request);

    /**
     * 
     * @param request
     * @return
     */
    JsonObject removeById(JsonObject request);
}
