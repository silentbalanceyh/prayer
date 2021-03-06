package com.prayer.uca;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.facade.kernel.Value;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebValidator {
    /**
     * 
     * @param name        需要验证的参数名
     * @param value        参数值
     * @param config    当前Validator的配置
     * @return
     * @throws AbstractWebException
     */
    boolean validate(String name, Value<?> value, JsonObject config) throws AbstractWebException;
}
