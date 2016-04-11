package com.prayer.facade.model.crucial;

import java.lang.reflect.Type;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.type.DataType;

/**
 * 所有数据类型必须实现Value<R> R：Raw Type T：Type
 * 
 * @author Lang
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Value<R> {
    /**
     * 获得类型的值
     * 
     * @return
     */
    @VertexApi(Api.READ)
    R getValue();

    /**
     * 设置Value的值
     * 
     * @param value
     */
    @VertexApi(Api.WRITE)
    void setValue(R value);

    /**
     * 获得Java的数据类型
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Type getType();

    /**
     * 获得Lyra类型描述
     * 
     * @return
     */
    @VertexApi(Api.READ)
    DataType getDataType();

    /**
     * 获取这种类型值的字面量
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String literal();
}
