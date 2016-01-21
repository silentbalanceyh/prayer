package com.prayer.facade.fun.builder;

import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface AutoIncrement {
    /**
     * 自动增长语句
     * @param schema
     * @return
     */
    String build(Schema schema);
}
