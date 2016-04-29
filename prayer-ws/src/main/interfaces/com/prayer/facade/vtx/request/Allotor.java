package com.prayer.facade.vtx.request;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Allotor {
    /**
     * 同步无回调模式
     * @param context
     * @return
     */
    @VertexApi(Api.TOOL)
    Envelop accept(RoutingContext context,Envelop envelop);
}
