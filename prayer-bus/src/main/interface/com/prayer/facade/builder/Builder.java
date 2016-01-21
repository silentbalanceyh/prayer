package com.prayer.facade.builder;

import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 元数据构造表的新接口Builder
 * @author Lang
 *
 */
public interface Builder {
    /**
     * 同步表信息的接口（添加/更新）
     * @param schema
     * @return
     * @throws AbstractDatabaseException
     */
    boolean synchronize(Schema schema) throws AbstractDatabaseException;
    /**
     * 删除表信息的接口
     * @param schema
     * @return
     * @throws AbstractDatabaseException
     */
    boolean purge(Schema schema) throws AbstractDatabaseException;
}
