package com.prayer.metadata;

import java.lang.reflect.Type;

import com.prayer.model.type.DataType;

/**
 * 所有数据类型必须实现Value<R> R：Raw Type T：Type
 * 
 * @author Lang
 */
public interface Value<R> {
	/**
	 * 获得类型的值
	 * 
	 * @return
	 */
	R getValue();

	/**
	 * 设置Value的值
	 * 
	 * @param value
	 */
	void setValue(R value);

	/**
	 * 获得Java的数据类型
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * 获得Lyra类型描述
	 * 
	 * @return
	 */
	DataType getDataType();
}
