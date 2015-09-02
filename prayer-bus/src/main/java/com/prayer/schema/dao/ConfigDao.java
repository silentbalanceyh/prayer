package com.prayer.schema.dao;

import com.prayer.exception.vertx.DataAccessException;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.VerticleModel;

/**
 * 
 * @author Lang
 *
 */
public interface ConfigDao {
	/**
	 * 插入配置（支持批量）
	 * @param verticle
	 * @return
	 * @throws DataAccessException
	 */
	VerticleModel insert(VerticleModel... verticle) throws DataAccessException;
	/**
	 * 更新配置（支持批量）
	 * @param verticle
	 * @return
	 * @throws DataAccessException
	 */
	VerticleModel update(VerticleModel... verticle) throws DataAccessException;
	/**
	 * 删除配置
	 * @param uniqueId
	 * @return
	 * @throws DataAccessException
	 */
	boolean deleteById(String uniqueId) throws DataAccessException;
	/**
	 * 删除配置
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	boolean deleteByName(String name) throws DataAccessException;
	/**
	 * 按照组名读取系统中所有的VerticleModel，返回的是一个VerticleChain对象
	 * @param name
	 * @return
	 */
	VerticleChain getByName(String name);
	/**
	 * 按照类名读取单个VerticleModel
	 * @param clazz
	 * @return
	 */
	VerticleModel getByClass(String clazz);
}
