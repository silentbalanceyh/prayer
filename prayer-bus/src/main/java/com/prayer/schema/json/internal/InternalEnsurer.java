package com.prayer.schema.json.internal;

import com.prayer.exception.AbstractSchemaException;

/**
 * Schema的内部接口，包内使用
 * 
 * @author Lang
 *
 */
interface InternalEnsurer {
	/**
	 * 主验证方法
	 * 
	 * @throws AbstractSchemaException
	 */
	void validate() throws AbstractSchemaException;

	/**
	 * 打断方法
	 * 
	 * @throws AbstractSchemaException
	 */
	void interrupt() throws AbstractSchemaException;
}
