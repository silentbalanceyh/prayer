package com.prayer.facade.metadata.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.facade.entity.Entity;
import com.prayer.model.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public interface PEScriptMapper extends IBatisMapper<Entity,String>{
    /**
     * 
     * @param name
     * @return
     */
    PEScript selectByName(@Param("name") String name);
}
