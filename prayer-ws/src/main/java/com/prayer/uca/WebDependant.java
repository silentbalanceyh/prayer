package com.prayer.uca;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.facade.kernel.Value;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebDependant extends WebConvertor, WebValidator {
    /**
     * 
     * @param name
     * @param value
     * @param dependID
     * @param config
     * @return
     * @throws AbstractWebException
     */
    boolean validate(String name, Value<?> value, Value<?> dependID, JsonObject config) throws AbstractWebException;
}
