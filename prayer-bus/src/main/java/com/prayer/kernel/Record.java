package com.prayer.kernel;

import java.util.concurrent.ConcurrentMap;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

/**
 * Record接口
 * 
 * @author Lang
 * @see
 */
public interface Record extends RecordPK, RecordDB{
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

    // ~ Metadata 元数据需要使用的相关接口 ===========================
    /**
     * 获取当前记录的模型名
     * 
     * @return
     */
    String identifier();
    /**
     * 根据列名获取field名称
     * @param column
     * @return
     * @throws AbstractDatabaseException
     */
    String toField(String column) throws AbstractDatabaseException;
    /**
     * 根据field名获取列名称
     * @param field
     * @return
     * @throws AbstractDatabaseException
     */
    String toColumn(String field) throws AbstractDatabaseException;
    /**
     * 获取fields的数据类型：name = DataType
     * @return
     */
    ConcurrentMap<String,DataType> fields();
}
