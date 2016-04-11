package com.prayer.facade.model.cache;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 私有接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface CacheOp {
    /**
     * 
     * @return
     */
    @VertexApi(Api.READ)
    int size();

    /**
     * 清除当前缓存
     */
    @VertexApi(Api.WRITE)
    void clean();
}
