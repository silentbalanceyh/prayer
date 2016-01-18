package com.prayer.facade.bus;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface ConfigService {
    // ~ Verticle Interface ==================================
    /**
     * 按Group从H2读取Verticle的配置信息
     * 
     * @param group
     * @return
     */
    ServiceResult<List<PEVerticle>> findVerticles(String group);

    /**
     * 从H2数据库中读取所有的Verticle配置
     * 
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PEVerticle>>> findVerticles();

    // ~ Route Interface =====================================
    /**
     * 读取主路由下的子路由
     * 
     * @return
     */
    ServiceResult<List<PERoute>> findRoutes(final String parent);

    /**
     * 读取所有的路由表
     * 
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PERoute>>> findRoutes();

    // ~ Uri Records ========================================
    /**
     * 
     * @param uri
     * @return
     */
    ServiceResult<ConcurrentMap<HttpMethod, PEUri>> findUri(String uri);

    // ~ Uri Parameter Rules ================================
    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PERule>>> findValidators(String uriId);

    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PERule>>> findConvertors(String uriId);
    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PERule>>> findDependants(String uriId);
    /**
     * 
     * @param workClass
     * @return
     */
    ServiceResult<PEAddress> findAddress(Class<?> workClass);

    /**
     * 
     * @param name
     * @return
     */
    ServiceResult<PEScript> findScript(String name);
}
