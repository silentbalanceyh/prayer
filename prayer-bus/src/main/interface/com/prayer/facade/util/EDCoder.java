package com.prayer.facade.util;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface EDCoder {
    /**
     * 
     * @param value
     * @return
     */
    @VertexApi(Api.TOOL)
    String encode(String value);
    /**
     * 
     * @param value
     * @return
     */
    @VertexApi(Api.TOOL)
    String decode(String value);
}
