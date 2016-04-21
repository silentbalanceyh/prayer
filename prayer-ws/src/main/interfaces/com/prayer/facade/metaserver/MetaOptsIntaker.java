package com.prayer.facade.metaserver;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.engine.opts.Options;
import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 公共接口，主要用于配置数据读取操作
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface MetaOptsIntaker extends Intaker<Options> {
    /**
     * 读取默认配置的接口
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Options ingest() throws AbstractLauncherException;
}
