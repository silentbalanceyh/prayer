package com.prayer.facade.engine.fun;

import com.prayer.model.business.behavior.ActResponse;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Invoker {
    /** **/
    ActResponse invoke(JsonObject params);
}
