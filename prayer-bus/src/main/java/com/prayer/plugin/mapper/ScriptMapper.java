package com.prayer.plugin.mapper;

import com.prayer.model.vertx.ScriptModel;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptMapper extends H2TMapper<ScriptModel,String>{
    /**
     * 
     * @param name
     * @return
     */
    ScriptModel selectByName(String name);
}
