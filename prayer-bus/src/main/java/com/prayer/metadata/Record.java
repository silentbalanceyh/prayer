package com.prayer.metadata;

import java.util.Set;

import com.prayer.model.meta.GenericSchema;

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
	void set(String name, Value<?> value);

	/**
	 * 
	 * @param name
	 * @param value
	 */
	void set(String name, String value);

	/**
	 * 从当前记录中获取对应属性值
	 * 
	 * @param name
	 * @return
	 */
	Value<?> get(String name);

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
	 * @return
	 */
	GenericSchema schema();
}
