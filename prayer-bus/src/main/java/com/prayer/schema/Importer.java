package com.prayer.schema;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.SerializationException;
import com.prayer.mod.meta.GenericSchema;

/**
 * 导入接口流程
 * 
 * @author Lang
 * @see
 */
public interface Importer {
	/**
	 * 1. 2XXXX Error，是否抛出系统异常，读写文件
	 * 
	 * @param filePath
	 * @throws AbstractSystemException
	 */
	void importFile() throws AbstractSystemException;

	/**
	 * 2. 1XXXX Error，验证JsonSchema文件
	 * 
	 * @throws AbstractSchemaException
	 */
	void ensureSchema() throws AbstractSchemaException;

	/**
	 * 3. 20004 Error，序列化JsonNode转换成节点类型
	 * @throws SerializationException
	 */
	GenericSchema transformModel() throws SerializationException;	
	/**
	 * 
	 * @return
	 */
	Ensurer getEnsurer();
}
