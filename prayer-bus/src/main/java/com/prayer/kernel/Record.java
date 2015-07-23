package com.prayer.kernel;

import java.util.Set;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.model.GenericSchema;

/**
 * Record接口
 * 
 * @author Lang
 * @see
 */
public interface Record {
	/**
	 * 针对当前记录设置字段值
	 * 
	 * @param name
	 * @param value
	 */
	void set(String name, Value<?> value) throws AbstractDatabaseException;

	/**
	 * 
	 * @param name
	 * @param value
	 */
	void set(String name, String value) throws AbstractDatabaseException;

	/**
	 * 从当前记录中获取对应属性值
	 * 
	 * @param name
	 * @return
	 */
	Value<?> get(String name) throws AbstractDatabaseException;

	/**
	 * 根据列名查找对应的属性值
	 * 
	 * @param column
	 * @return
	 * @throws AbstractDatabaseException
	 */
	Value<?> column(String column) throws AbstractDatabaseException;

	/**
	 * 获取当前记录的模型名
	 * 
	 * @return
	 */
	String identifier();

	/**
	 * 获取当前记录的字段集合
	 * 
	 * @return
	 */
	Set<String> columns();

	/**
	 * 获取当前Record对应的Schema
	 * 
	 * @return
	 */
	GenericSchema schema();

	/**
	 * 获取当前记录的操作表名
	 * 
	 * @return
	 */
	String table();
}
