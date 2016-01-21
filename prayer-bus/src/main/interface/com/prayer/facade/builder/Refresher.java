package com.prayer.facade.builder;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
public interface Refresher {
    /**
     * 构造更新用的SQL语句
     * @param schema
     */
    String buildAlterSQL(Schema schema) throws AbstractDatabaseException;
}
