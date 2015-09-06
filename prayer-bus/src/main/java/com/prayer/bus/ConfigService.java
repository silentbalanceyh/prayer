package com.prayer.bus;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.RouteModel;

/**
 * 
 * @author Lang
 *
 */
public interface ConfigService {
	/**
	 * 按Group从H2读取Verticle的配置信息
	 * 
	 * @param group
	 * @return
	 */
	ServiceResult<VerticleChain> findVerticles(final String group);

	/**
	 * 从Json文件导入Verticle配置数据到H2数据库中
	 * 
	 * @param jsonPath
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, VerticleChain>> importToH2(final String jsonPath);

	/**
	 * 从H2数据库中读取所有的Verticle配置
	 * 
	 * @return
	 */
	ServiceResult<ConcurrentMap<String, VerticleChain>> readFromH2();

	/**
	 * 读取主路由下的子路由
	 * 
	 * @return
	 */
	ServiceResult<List<RouteModel>> findByParent(final String parent);

	/**
	 * 读取所有的路由表
	 * 
	 * @return
	 */
	ServiceResult<List<RouteModel>> findAll();
}
