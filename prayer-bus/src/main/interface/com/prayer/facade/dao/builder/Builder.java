package com.prayer.facade.dao.builder;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.schema.Schema;

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
