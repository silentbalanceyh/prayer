package com.prayer.bus.schema;

import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaService {
	/**
	 * Json文件 -> H2 Database
	 * 1. Add Schema
	 * 2. Save Schema
	 * @param filePath
	 * @return
	 */
	ServiceResult<GenericSchema> syncSchema(String filePath);
	/**
	 * H2 Database -> SQL Database
	 * 1. Add Metadata
	 * 2. Save Metadata
	 * @param schema
	 * @return
	 */
	ServiceResult<GenericSchema> syncMetadata(GenericSchema schema);
	/**
	 * 从H2 Databsase中读取Schema定义信息
	 * @param identifier
	 * @return
	 */
	ServiceResult<GenericSchema> findSchema(final String identifier);
	/**
	 * 从H2 Database中删除Schema定义信息
	 * 1.删除H2 Database中的数据
	 * 2.删除数据库中的数据
	 * @param identifier
	 * @return
	 */
	ServiceResult<Boolean> removeSchema(final String identifier);
}
