package com.prayer.facade.vtx.request;

import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public interface Alloter {
    /**
     * 
     * @param request
     * @param bus
     */
    void accept(RoutingContext context);
}
