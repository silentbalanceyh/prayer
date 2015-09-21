package com.prayer.uca;

import com.prayer.exception.AbstractWebException;
import com.prayer.kernel.Value;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebConvertor {
    /**
     * 将一个类型的Value<?>转换成另外一个类型的Value<?>
     * @param name
     * @param value
     * @param config
     * @return
     * @throws AbstractWebException
     */
    Value<?> convert(String name, Value<?> value, JsonObject config) throws AbstractWebException;
}
