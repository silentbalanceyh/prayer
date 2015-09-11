package com.prayer.bus;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.AddressModel;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.model.h2.vx.UriModel;

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
	ServiceResult<VerticleChain> findVerticles(String group);

	/**
	 * 从H2数据库中读取所有的Verticle配置
	 * 
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, VerticleChain>> findVerticles();

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
	ServiceResult<UriModel> findUri(String uri,HttpMethod method);
	
	// ~ Uri Parameter Rules ================================
	/**
	 * 
	 * @param uriId
	 * @return
	 */
	ServiceResult<ConcurrentMap<String,List<RuleModel>>> findValidators(String uriId);
	/**
	 * 
	 * @param uriId
	 * @return
	 */
	ServiceResult<ConcurrentMap<String,List<RuleModel>>> findConvertors(String uriId);
	/**
	 * 
	 * @param workClass
	 * @return
	 */
	ServiceResult<AddressModel> findAddress(Class<?> workClass);
}
