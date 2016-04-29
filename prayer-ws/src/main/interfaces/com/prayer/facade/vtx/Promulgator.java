package com.prayer.facade.vtx;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.VertxOptions;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Promulgator {
    /**
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    boolean deploy(VertxOptions options) throws AbstractException;
}
