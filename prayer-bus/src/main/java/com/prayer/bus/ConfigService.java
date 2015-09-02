package com.prayer.bus;

import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;

/**
 * 
 * @author Lang
 *
 */
public interface ConfigService {
	/**
	 * 按Group从H2读取Verticle的配置信息
	 * @param group
	 * @return
	 */
	ServiceResult<VerticleChain> findVerticles(final String group);
	/**
	 * 从Json文件导入Verticle配置数据到H2数据库中
	 * @param jsonPath
	 * @return
	 */
	ServiceResult<ConcurrentMap<String,VerticleChain>> readFromJson(final String jsonPath);
}
