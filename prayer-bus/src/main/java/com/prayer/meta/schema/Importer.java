package com.prayer.meta.schema;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;

/**
 * 导入接口流程
 * @author Lang
 * @see
 */
public interface Importer {
	/**
	 * 1. 2XXXX Error
	 * @param filePath
	 * @throws AbstractSystemException
	 */
	void importFile() throws AbstractSystemException;
	/**
	 * 2. 1XXXX Error
	 * @throws AbstractSchemaException
	 */
	void ensureSchema() throws AbstractSchemaException;
	/**
	 * 
	 */
	void loadData();
	/**
	 * 
	 */
	void finalizeImport();
}
