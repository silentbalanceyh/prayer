package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PERoute;

/**
 * 
 * @author Lang
 *
 */
public interface RouteMapper extends H2TMapper<PERoute, String>{
    /**
     * 
     * @param parent
     * @param path
     * @return
     */
    PERoute selectByPath(@Param("parent") String parent, @Param("path") String path);
    /**
     * 
     * @param parent
     * @return
     */
    List<PERoute> selectByParent(@Param("parent") String parent);
}
