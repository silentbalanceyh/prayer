package com.prayer.facade.dao.schema;

import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;

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
