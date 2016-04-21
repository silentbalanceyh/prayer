package com.prayer.old.console.commands;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Command {
    /**
     * 
     * @param params
     * @return
     */
    JsonObject execute(final String... args);
}
