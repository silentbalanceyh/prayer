package com.prayer.db.mybatis;

import java.util.List;

import com.prayer.mod.sys.MetaModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface MetaMapper {
	/**
	 * 添加元数据记录
	 * 
	 * @param meta
	 * @return 传入数据影响行数
	 */
	int insert(MetaModel meta);

	/**
	 * 批量插入
	 * 
	 * @param metas
	 * @return
	 */
	int batchInsert(List<MetaModel> metas);

	/**
	 * 更新元数据记录
	 * 
	 * @param meta
	 * @return 更新数据影响行数
	 */
	int update(MetaModel meta);

	/**
	 * 根据Meta的ID删除Meta记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(String uniqueId);

	/**
	 * 根据Meta的namespace和name删除Meta记录
	 * 
	 * @param namespace
	 * @param name
	 * @return
	 */
	boolean deleteByModel(String namespace, String name);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<String> ids);

	/**
	 * 根据Meta的ID获取Meta记录
	 * 
	 * @param uniqueId
	 * @return
	 */
	MetaModel selectById(String uniqueId);

	/**
	 * 根据Meta的namespace和name获取Meta记录
	 * 
	 * @param namespace
	 * @param name
	 * @return
	 */
	MetaModel selectByModel(String namespace, String name);

	/**
	 * 读取所有的Meta的记录
	 * 
	 * @return
	 */
	List<MetaModel> selectAll();

	/**
	 * 清除数据
	 * 
	 * @return
	 */
	boolean purgeData();
}
