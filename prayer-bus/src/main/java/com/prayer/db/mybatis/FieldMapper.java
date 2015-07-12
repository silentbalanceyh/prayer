package com.prayer.db.mybatis;

import java.util.List;

import com.prayer.mod.meta.FieldModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface FieldMapper {
	/**
	 * 添加Field字段记录
	 * 
	 * @param field
	 * @return 插入Field数据影响行数
	 */
	int insert(FieldModel field);

	/**
	 * 批量添加
	 * 
	 * @param field
	 * @return
	 */
	int batchInsert(List<FieldModel> field);

	/**
	 * 更新Field字段记录
	 * 
	 * @param field
	 * @return 更新Field数据影响行数
	 */
	int update(FieldModel field);

	/**
	 * 根据Field的ID删除记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(String uniqueId);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<String> ids);

	/**
	 * 根据Field的metaId删除记录
	 * 
	 * @param metaId
	 * @return
	 */
	boolean deleteByMeta(String metaId);

	/**
	 * 根据Field的ID获取Field记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	FieldModel selectById(String uniqueId);

	/**
	 * 根据Field的metaId获取Field记录
	 * 
	 * @param metaId
	 * @return
	 */
	FieldModel selectByMeta(String metaId);

	/**
	 * 读取所有的Field的记录
	 * 
	 * @return
	 */
	List<FieldModel> selectAll();

	/**
	 * 清除数据
	 * 
	 * @return
	 */
	boolean purgeData();
}
