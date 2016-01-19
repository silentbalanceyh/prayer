package com.prayer.facade.record;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Value;

/**
 * Record接口
 * 
 * @author Lang
 * @see
 */
public interface Record extends RecordPrimaryKey, RecordDatabase, RecordMeta{
    /**
     * 针对当前记录设置字段值
     * 
     * @param name
     * @param value
     */
    void set(String name, Value<?> value) throws AbstractDatabaseException;

    /**
     * 
     * @param name
     * @param value
     */
    void set(String name, String value) throws AbstractDatabaseException;

    /**
     * 从当前记录中获取对应属性值
     * 
     * @param name
     * @return
     */
    Value<?> get(String name) throws AbstractDatabaseException;
}
