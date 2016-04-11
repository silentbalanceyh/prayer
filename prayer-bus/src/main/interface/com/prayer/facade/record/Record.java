package com.prayer.facade.record;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * Record接口
 * 
 * @author Lang
 * @see
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Record extends PrimaryKey, Database, Metadata{
    /**
     * 针对当前记录设置字段值
     * 
     * @param name
     * @param value
     */
    @VertexApi(Api.WRITE)
    void set(String name, Value<?> value) throws AbstractDatabaseException;

    /**
     * 
     * @param name
     * @param value
     */
    @VertexApi(Api.WRITE)
    void set(String name, String value) throws AbstractDatabaseException;
    /**
     * 从当前记录中获取对应属性值
     * 
     * @param name
     * @return
     */
    @VertexApi(Api.READ)
    Value<?> get(String name) throws AbstractDatabaseException;
}
