package com.prayer.facade.vtx.request;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PEUri;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public interface Resolver {
    /**
     * 直接返回String类型的参数值
     * 
     * @param request
     * @param uri
     * @return
     * @throws AbstractException
     */
    JsonObject resolve(RoutingContext context, PEUri uri) throws AbstractWebException;
}
