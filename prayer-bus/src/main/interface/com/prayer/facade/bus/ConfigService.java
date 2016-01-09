package com.prayer.facade.bus;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.PEAddress;
import com.prayer.model.vertx.RouteModel;
import com.prayer.model.vertx.RuleModel;
import com.prayer.model.vertx.PEScript;
import com.prayer.model.vertx.UriModel;
import com.prayer.model.vertx.PEVerticle;

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
    ServiceResult<List<RouteModel>> findRoutes(final String parent);

    /**
     * 读取所有的路由表
     * 
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<RouteModel>>> findRoutes();

    // ~ Uri Records ========================================
    /**
     * 
     * @param uri
     * @return
     */
    ServiceResult<ConcurrentMap<HttpMethod, UriModel>> findUri(String uri);

    // ~ Uri Parameter Rules ================================
    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<RuleModel>>> findValidators(String uriId);

    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<RuleModel>>> findConvertors(String uriId);
    /**
     * 
     * @param uriId
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<RuleModel>>> findDependants(String uriId);
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
