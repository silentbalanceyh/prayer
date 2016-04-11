package com.prayer.facade.business.configuration;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERule;

/**
 * 组件读取专用接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface ConfigUCAObtainer {
    /**
     * 依赖器
     * 
     * @param uriId
     * @return
     */
    ConcurrentMap<String, List<PERule>> dependants(String uriId) throws AbstractException;

    /**
     * 转换器
     * 
     * @param uriId
     * @return
     */
    ConcurrentMap<String, List<PERule>> convertors(String uriId) throws AbstractException;

    /**
     * 验证器
     * 
     * @param uriId
     * @return
     */
    ConcurrentMap<String, List<PERule>> validators(String uriId) throws AbstractException;
}
