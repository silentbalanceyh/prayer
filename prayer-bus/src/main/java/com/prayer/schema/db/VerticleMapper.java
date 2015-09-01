package com.prayer.schema.db;

import java.util.List;

import com.prayer.model.h2.vx.VerticleModel;

/**
 * 
 * @author Lang
 *
 */
public interface VerticleMapper {
	/**
	 * 
	 * @param verticle
	 * @return
	 */
	int insert(VerticleModel verticle);
	/**
	 * 
	 * @param verticle
	 * @return
	 */
	int batchInsert(List<VerticleModel> verticle);
	/**
	 * 
	 * @param verticle
	 * @return
	 */
	int update(VerticleModel verticle);
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(String uniqueId);
	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean deleteByName(String name);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<String> ids);
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	VerticleModel selectById(String uniqueId);
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<VerticleModel> selectByName(String name);
	/**
	 * 
	 * @return
	 */
	List<VerticleModel> selectAll();
	/**
	 * 清除数据
	 * @return
	 */
	boolean purgeData();
}
