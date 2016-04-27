package com.prayer.facade.vtx.uca;

import com.prayer.facade.model.crucial.Value;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebValidator {
    /**
     * 
     * @param name
     * @param value
     * @param config
     * @return
     */
    Envelop validate(String name, Value<?> value, JsonObject config);
}
