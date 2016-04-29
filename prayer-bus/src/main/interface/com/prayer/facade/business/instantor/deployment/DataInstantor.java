package com.prayer.facade.business.instantor.deployment;

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
public interface DataInstantor {
    /**
     * 1.初始化某个目录下的所有数据
     * @param folder
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.WRITE)
    boolean push(String folder) throws AbstractException;
}
