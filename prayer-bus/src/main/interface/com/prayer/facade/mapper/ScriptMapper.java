package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

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
    ScriptModel selectByName(@Param("name") String name);
}
