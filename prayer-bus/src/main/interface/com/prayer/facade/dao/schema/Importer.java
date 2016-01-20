package com.prayer.facade.dao.schema;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
public interface Importer {
    /**
     * 从路径中读取Schema
     * 
     * @param path
     * @return
     */
    Schema read(String path) throws AbstractSchemaException,AbstractSystemException;
}
