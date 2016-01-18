package com.prayer.facade.schema.verifier;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.facade.kernel.Schema;

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
    Schema readFrom(String path) throws AbstractSchemaException,AbstractSystemException;

    /**
     * 将一个Schema信息
     * 
     * @param schema
     * @return
     */
    Schema syncSchema(Schema schema);
}
