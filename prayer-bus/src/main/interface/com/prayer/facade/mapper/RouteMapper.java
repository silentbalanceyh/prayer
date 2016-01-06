package com.prayer.facade.mapper;

import java.util.List;

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
    RouteModel selectByPath(String parent, String path);
    /**
     * 
     * @param parent
     * @return
     */
    List<RouteModel> selectByParent(String parent);
}
