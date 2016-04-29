package com.prayer.business.fun;

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
public interface ActMethod {
    /** **/
    JsonObject execute(ActRequest request) throws ScriptException, AbstractException;
}
