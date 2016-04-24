package com.prayer.facade.vtx.route;

import io.vertx.ext.web.Router;

/**
 * 
 * @author Lang
 *
 */
public interface Fabricator {
    /**
     * 
     * @param router
     */
    void immitRouter(Router router);
}
