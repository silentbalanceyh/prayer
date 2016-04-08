package com.prayer.facade.record;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface Metadata {
    // ~ Metadata 元数据需要使用的相关接口 ===========================
    /**
     * 获取当前记录的模型名
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String identifier();

    /**
     * 根据列名获取field名称
     * 
     * @param column
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.READ)
    String toField(String column) throws AbstractDatabaseException;

    /**
     * 根据field名获取列名称
     * 
     * @param field
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.READ)
    String toColumn(String field) throws AbstractDatabaseException;

    /**
     * 获取fields的数据类型：name = DataType
     * 
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String, DataType> fields();
}
