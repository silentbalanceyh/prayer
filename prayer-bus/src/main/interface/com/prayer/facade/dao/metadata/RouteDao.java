package com.prayer.facade.dao.metadata;

import java.util.List;

import com.prayer.model.vertx.PERoute;

/**
 * 
 * @author Lang
 *
 */
public interface RouteDao extends TemplateDao<PERoute, String>{
    /**
     * 
     * @param parent
     * @param path
     * @return
     */
    PERoute getByPath(String parent, String path);
    /**
     * 
     * @param parent
     * @return
     */
    List<PERoute> getByParent(String parent);
}
