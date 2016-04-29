package com.prayer.facade.vtx.route;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

import io.vertx.ext.web.Router;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Fabricator {
    /**
     * 
     * @param router
     */
    @VertexApi(Api.TOOL)
    void immitRouter(Router router);
}
