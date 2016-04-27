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
public interface Encryptor {
    /**
     * 
     * @param value
     * @return
     */
    @VertexApi(Api.TOOL)
    String encrypt(String value);
}
