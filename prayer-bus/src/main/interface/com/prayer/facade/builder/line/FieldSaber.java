package com.prayer.facade.builder.line;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.meta.database.PEField;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface FieldSaber {
    /**
     * 行构造器
     * 
     * @param field
     * @return
     */
    @VertexApi(Api.TOOL)
    String buildLine(PEField field);
}
