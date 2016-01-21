package com.prayer.facade.record;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;
/**
 * 
 * @author Lang
 *
 */
interface RecordDatabase {

    /**
     * 根据列名查找对应的属性值
     * 
     * @param column
     * @return
     * @throws AbstractDatabaseException
     */
    Value<?> column(String column) throws AbstractDatabaseException;

    /**
     * 获取当前记录的操作表名
     * 
     * @return
     */
    String table();
    /**
     * 获取当前记录的字段集合
     * 
     * @return
     */
    Set<String> columns();
    /**
     * 获取当前记录的列类型Mapping
     * @return
     */
    ConcurrentMap<String,DataType> columnTypes();
}
