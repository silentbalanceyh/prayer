package com.prayer.facade.dao.metadata;

import java.util.List;

import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.model.meta.vertx.PERoute;

/**
 * 
 * @author Lang
 *
 */
public interface RouteDao extends MetaAccessor<PERoute, String>{
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
