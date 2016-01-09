package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEVerticle;

/**
 * 
 * @author Lang
 *
 */
public interface VerticleMapper extends H2TMapper<PEVerticle, String> {
    /**
     * 
     * @param name
     * @return
     */
    boolean deleteByName(@Param("name") Class<?> name);
    /**
     * 
     * @param name
     * @return
     */
    PEVerticle selectByName(@Param("name") Class<?> name);

    /**
     * 
     * @param group
     * @return
     */
    List<PEVerticle> selectByGroup(@Param("group") String group);
}
