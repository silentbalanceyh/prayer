package com.prayer.meta;

import com.prayer.exception.AbstractBuilderException;
import com.prayer.model.meta.GenericSchema;

/**
 * 元数据构造表的接口
 * 
 * @author Lang
 *
 */
public interface Builder {
	/**
	 * 创建数据表
	 * 
	 * @param schema
	 */
	boolean createTable();

	/**
	 * 检查表是否存在
	 * 
	 * @return
	 */
	boolean existTable();

	/**
	 * 同步数据表
	 * 
	 * @param schema
	 */
	boolean syncTable(GenericSchema schema);

	/**
	 * 删除数据表
	 * 
	 * @param schema
	 */
	boolean purgeTable();
	/**
	 * 获取执行步骤中的Exception
	 * @return
	 */
	AbstractBuilderException getError();
}
