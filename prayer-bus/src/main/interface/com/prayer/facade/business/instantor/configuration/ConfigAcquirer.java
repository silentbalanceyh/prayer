package com.prayer.facade.business.instantor.configuration;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PEVerticle;

/**
 * 私有接口，读取所有内容使用的接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface ConfigAcquirer {
    /**
     * 读取所有的Verticle，按照Group进行分组：group
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String,List<PEVerticle>> verticles() throws AbstractException;
    /**
     * 读取所有的Route，按照父路径进行分组：parent
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String,List<PERoute>> routes() throws AbstractException;
}
