package com.prayer.facade.engine.metaserver.h2;

import org.h2.tools.Server;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface H2Reference {
    /**
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    Server getWebRef() throws AbstractException;

    /**
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    Server getTcpRef() throws AbstractException;
}
