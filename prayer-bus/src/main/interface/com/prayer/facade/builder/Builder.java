package com.prayer.facade.builder;

import java.util.Set;

import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;

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
    /**
     * 重载，删除表信息
     * @param table
     * @return
     * @throws AbstractDatabaseException
     */
    boolean purge(String table) throws AbstractDatabaseException;
    /**
     * 重载，删除多个表
     * @param tables
     * @return
     * @throws AbstractDatabaseException
     */
    boolean purge(Set<String> tables) throws AbstractException;
}
