package com.prayer.facade.business.deployment.acus;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 发布用接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface DeployAcus {
    /**
     * 发布数据专用接口
     * 
     * @param folder
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.WRITE)
    boolean deploy(String folder) throws AbstractException;

    /**
     * 删除数据专用接口
     * 
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.WRITE)
    boolean purge() throws AbstractException;
}
