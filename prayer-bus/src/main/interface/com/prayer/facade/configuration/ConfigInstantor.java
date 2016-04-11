package com.prayer.facade.configuration;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.DIRECT)
public interface ConfigInstantor extends ConfigAcquirer, ConfigObjecter, ConfigUCAObtainer {
    /**
     * 根据Uri的路径读取对应的Uri
     * 
     * @param uri
     * @return
     */
    ConcurrentMap<HttpMethod, PEUri> uris(String path) throws AbstractException;

    /**
     * 根据Parent读取子路由
     * 
     * @param parent
     * @return
     */
    List<PERoute> routes(String parent) throws AbstractException;

    /**
     * 根据Group读取对应的Verticle
     * 
     * @param group
     * @return
     */
    List<PEVerticle> verticles(String group) throws AbstractException;
}
