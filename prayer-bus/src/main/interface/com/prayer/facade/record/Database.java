package com.prayer.facade.record;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface Database {

    /**
     * 根据列名查找对应的属性值
     * 
     * @param column
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.READ)
    Value<?> column(String column) throws AbstractDatabaseException;

    /**
     * 获取当前记录的操作表名
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String table();

    /**
     * 获取当前记录的字段集合
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Set<String> columns();

    /**
     * 获取当前记录的列类型Mapping
     * 
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String, DataType> columnTypes();
}
