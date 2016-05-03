package com.prayer.facade.business.instantor.uca;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.DIRECT)
public interface UtilInstantor {
    /**
     * 使用field = value检查identifier记录是否存在
     * @param identifier
     * @param field
     * @param value
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    boolean checkUnique(String identifier, String field, String value) throws AbstractException;
}
