package com.prayer.facade.metaserver;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Warranter {
    /**
     * 保证
     * @param inceptor
     * @param key
     */
    @VertexApi(Api.READ)
    void warrant(Inceptor inceptor, String... keys) throws AbstractLauncherException;
}
