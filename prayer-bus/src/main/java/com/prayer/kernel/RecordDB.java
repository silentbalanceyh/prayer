package com.prayer.kernel;

import java.util.Set;

import com.prayer.exception.AbstractDatabaseException;
/**
 * 
 * @author Lang
 *
 */
interface RecordDB {

	/**
	 * 根据列名查找对应的属性值
	 * 
	 * @param column
	 * @return
	 * @throws AbstractDatabaseException
	 */
	Value<?> column(String column) throws AbstractDatabaseException;

	/**
	 * 获取当前记录的操作表名
	 * 
	 * @return
	 */
	String table();
	/**
	 * 获取当前记录的字段集合
	 * 
	 * @return
	 */
	Set<String> columns();
}
