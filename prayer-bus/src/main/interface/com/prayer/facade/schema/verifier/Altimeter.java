package com.prayer.facade.schema.verifier;

import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 另外一种类型的Verifier，用于处理更新流程
 * @author Lang
 *
 */
public interface Altimeter {
    /**
     * 属于Verifier的子验证器，所以用来抛出相关异常
     * @param data
     * @throws AbstractSchemaException
     */
    void verify(Schema schema) throws AbstractSchemaException; 
}
