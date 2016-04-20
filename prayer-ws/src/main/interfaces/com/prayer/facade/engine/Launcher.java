package com.prayer.facade.engine;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 启动器
 * 
 * @author Lang
 *
 */

@VertexPoint(Interface.INTERNAL)
public interface Launcher {
    /**
     * 
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    void start() throws AbstractException;

    /**
     * 
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    void stop() throws AbstractException;
}
