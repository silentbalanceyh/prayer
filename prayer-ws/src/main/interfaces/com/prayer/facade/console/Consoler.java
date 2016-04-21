package com.prayer.facade.console;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Consoler {
    /**
     * 执行当前的Consoler
     * @param args
     * @return
     */
    @VertexApi(Api.TOOL)
    boolean start();
}
