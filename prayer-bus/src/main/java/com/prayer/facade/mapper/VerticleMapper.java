package com.prayer.facade.mapper;

import java.util.List;

import com.prayer.model.h2.vertx.VerticleModel;

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
    boolean deleteByName(String name);
    /**
     * 
     * @param name
     * @return
     */
    VerticleModel selectByName(String name);

    /**
     * 
     * @param group
     * @return
     */
    List<VerticleModel> selectByGroup(String group);
}
