package com.prayer.facade.fun.endpoint;

import javax.script.ScriptException;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActRequest;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Behavior {
    /** **/
    JsonObject dispatch(ActRequest request) throws ScriptException, AbstractException;
}
