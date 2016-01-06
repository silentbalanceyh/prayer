package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.model.vertx.RouteModel;

/**
 * 
 * @author Lang
 *
 */
public interface RouteDao extends TemplateDao<RouteModel, String>{
    /**
     * 
     * @param parent
     * @param path
     * @return
     */
    RouteModel getByPath(String parent, String path);
    /**
     * 
     * @param parent
     * @return
     */
    List<RouteModel> getByParent(String parent);
}
