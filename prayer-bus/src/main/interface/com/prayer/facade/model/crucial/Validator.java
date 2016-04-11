package com.prayer.facade.model.crucial;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 格式校验接口
 *
 * @author Lang
 * @see
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Validator {
    /**
     * 校验字符串格式
     * 
     * @return
     */
    @VertexApi(Api.TOOL)
    boolean validate(Value<?> value, Object... params) throws AbstractDatabaseException;
}
