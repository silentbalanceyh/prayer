package com.prayer.facade.builder;

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
    String buildAlertSQL(Schema schema);
}
