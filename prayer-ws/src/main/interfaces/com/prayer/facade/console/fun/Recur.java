package com.prayer.facade.console.fun;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Recur {
    /** **/
    void execute(JsonObject params);
}
