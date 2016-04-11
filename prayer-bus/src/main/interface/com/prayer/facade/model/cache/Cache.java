package com.prayer.facade.model.cache;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * Cache接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Cache extends CacheOp{
    /**
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    <V> V get(String key);

    /**
     * 
     * @param key
     * @param value
     */
    @VertexApi(Api.WRITE)
    <V> void put(String key, V value);
}
