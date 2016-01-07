package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.RouteModel;

/**
 * 
 * @author Lang
 *
 */
public interface RouteMapper extends H2TMapper<RouteModel, String>{
    /**
     * 
     * @param parent
     * @param path
     * @return
     */
    RouteModel selectByPath(@Param("parent") String parent, @Param("path") String path);
    /**
     * 
     * @param parent
     * @return
     */
    List<RouteModel> selectByParent(@Param("parent") String parent);
}
