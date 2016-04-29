package com.prayer.facade.vtx.route;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PERoute;

import io.vertx.ext.web.Route;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface HubFabricator {
    /**
     * 
     * @param route
     * @param entity
     */
    @VertexApi(Api.TOOL)
    void immitRoute(Route routeRef, PERoute entity) throws AbstractWebException;
}
