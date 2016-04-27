package com.prayer.facade.vtx.uca;

import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebConvertor {
    /**
     * 
     * @param name
     * @param value
     * @param config
     * @return
     * @throws AbstractWebException
     */
    Envelop convert(String name, Value<?> value, JsonObject config);
}
