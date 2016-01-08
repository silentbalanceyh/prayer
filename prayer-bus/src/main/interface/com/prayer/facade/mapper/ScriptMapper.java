package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptMapper extends H2TMapper<PEScript,String>{
    /**
     * 
     * @param name
     * @return
     */
    PEScript selectByName(@Param("name") String name);
}
