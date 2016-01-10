package com.prayer.facade.metadata.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptMapper extends IBatisMapper<PEScript,String>{
    /**
     * 
     * @param name
     * @return
     */
    PEScript selectByName(@Param("name") String name);
}
