package com.prayer.bus;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.UriModel;

/**
 * 
 * @author Lang
 *
 */
public interface DeployService {
	// ~ OOB Deployment =======================================
	/**
	 * 用于初始化OOB数据的Deploy Service
	 */
	ServiceResult<Boolean> deployPrayerData();

	// ~ Verticle Deployment ==================================

	/**
	 * 从Json文件导入Verticle配置数据到H2数据库中
	 * 
	 * @param jsonPath
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, VerticleChain>> importVerticles(String jsonPath);

	/**
	 * 删除所有的Verticle配置信息
	 * 
	 * @return
	 */
	ServiceResult<Boolean> purgeVerticles();

	// ~ Route Deployment =====================================

	/**
	 * 从Json文件导入Route配置路由表到H2数据库中
	 * 
	 * @param jsonPath
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, List<RouteModel>>> importRoutes(String jsonPath);

	/**
	 * 删除所有的Routes配置信息
	 * 
	 * @return
	 */
	ServiceResult<Boolean> purgeRoutes();

	// ~ Uri Deployment =======================================
	/**
	 * 从Json文件导入Uri配置数据到H2数据库中
	 * 
	 * @param jsonPath
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, UriModel>> importUris(String jsonPath);

	/**
	 * 删除系统中所有的Uris配置信息
	 * 
	 * @return
	 */
	ServiceResult<Boolean> purgeUris();
}
