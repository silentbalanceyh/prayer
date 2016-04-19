package com.prayer.facade.engine.metaserver.h2;

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
@VertexPoint(Interface.INTERNAL)
public interface H2Server extends H2Messages {
    /**
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    boolean start() throws AbstractException;
    /**
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    boolean stop() throws AbstractException;
}
