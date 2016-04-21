package com.prayer.facade.vtx.config;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;
/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface EngineOptsIntaker<K,V> extends Intaker<ConcurrentMap<K,V>> {
    /**
     * 读取默认配置的接口
     */
    @VertexApi(Api.READ)
    ConcurrentMap<K,V> ingest() throws AbstractException;
}
