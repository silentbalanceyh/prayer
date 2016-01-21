package com.prayer.facade.record;

import java.util.concurrent.ConcurrentMap;

import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
interface RecordMeta {
    // ~ Metadata 元数据需要使用的相关接口 ===========================
    /**
     * 获取当前记录的模型名
     * 
     * @return
     */
    String identifier();

    /**
     * 根据列名获取field名称
     * 
     * @param column
     * @return
     * @throws AbstractDatabaseException
     */
    String toField(String column) throws AbstractDatabaseException;

    /**
     * 根据field名获取列名称
     * 
     * @param field
     * @return
     * @throws AbstractDatabaseException
     */
    String toColumn(String field) throws AbstractDatabaseException;

    /**
     * 获取fields的数据类型：name = DataType
     * 
     * @return
     */
    ConcurrentMap<String, DataType> fields();
}
