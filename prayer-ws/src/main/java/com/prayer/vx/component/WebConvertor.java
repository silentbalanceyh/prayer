package com.prayer.vx.component;

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
	 * 
	 * @param name
	 * @param value
	 * @param config
	 * @return
	 * @throws AbstractWebException
	 */
	String convert(String name, Value<?> value, JsonObject config) throws AbstractWebException;
}
