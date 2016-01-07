package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.VerticleModel;

/**
 * 
 * @author Lang
 *
 */
public interface VerticleMapper extends H2TMapper<VerticleModel, String> {
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
    VerticleModel selectByName(@Param("name") Class<?> name);

    /**
     * 
     * @param group
     * @return
     */
    List<VerticleModel> selectByGroup(@Param("group") String group);
}
