package com.prayer.schema.dao;

import java.util.List;

import com.prayer.exception.AbstractTransactionException;
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
	List<VerticleModel> insert(VerticleModel... verticle) throws AbstractTransactionException;
	/**
	 * 更新配置（支持批量）
	 * @param verticle
	 * @return
	 * @throws DataAccessException
	 */
	VerticleModel update(VerticleModel verticle) throws AbstractTransactionException;
	/**
	 * 删除配置
	 * @param uniqueId
	 * @return
	 * @throws DataAccessException
	 */
	boolean deleteById(String uniqueId) throws AbstractTransactionException;
	/**
	 * 删除配置
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	boolean deleteByName(String name) throws AbstractTransactionException;
	/**
	 * 按照组名读取系统中所有的VerticleModel，返回的是一个VerticleChain对象
	 * @param group
	 * @return
	 */
	VerticleChain getByGroup(String group);
	/**
	 * 按照类名读取单个VerticleModel
	 * @param clazz
	 * @return
	 */
	VerticleModel getByClass(String clazz);
}
