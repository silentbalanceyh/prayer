package com.prayer.db.mybatis;

import java.util.List;

import com.prayer.mod.meta.KeyModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface KeyMapper { 
	/**
	 * 添加记录
	 * 
	 * @param key
	 * @return 插入数据影响行数
	 */
	int insert(KeyModel key);

	/**
	 * 批量添加
	 * 
	 * @param keys
	 * @return
	 */
	int batchInsert(List<KeyModel> keys);

	/**
	 * 更新记录
	 * 
	 * @param key
	 * @return 更新数据影响行数
	 */
	int update(KeyModel key);

	/**
	 * 根据Meta的metaId删除Key记录
	 * 
	 * @param metaId
	 * @return
	 */
	boolean deleteByMeta(String metaId);

	/**
	 * 根据Key的ID删除Key记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(String uniqueId);

	/**
	 * 批量删除
	 * 
	 * @param keys
	 * @return
	 */
	boolean batchDelete(List<String> keyIds);

	/**
	 * 根据Key的ID获取Key记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	KeyModel selectById(String uniqueId);

	/**
	 * 根据Meta的metaId获取Key记录的集合
	 * 
	 * @param metaId
	 * @return
	 */
	List<KeyModel> selectByMeta(String metaId);

	/**
	 * 读取所有的Key的数据信息，返回List
	 * 
	 * @return
	 */
	List<KeyModel> selectAll();

	/**
	 * 清除数据
	 * 
	 * @return
	 */
	boolean purgeData();
}
