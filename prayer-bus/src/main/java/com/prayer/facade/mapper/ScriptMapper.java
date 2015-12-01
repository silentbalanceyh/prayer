package com.prayer.facade.mapper;

import com.prayer.model.h2.vertx.ScriptModel;

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