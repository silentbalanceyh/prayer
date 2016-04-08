package com.prayer.facade.schema.verifier;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 另外一种类型的Verifier，用于处理更新流程
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Altimeter {
    /**
     * 属于Verifier的子验证器，所以用来抛出相关异常
     * @param data
     * @throws AbstractSchemaException
     */
    @VertexApi(Api.TOOL)
    void verify(Schema schema) throws AbstractException; 
}
