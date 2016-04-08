package com.prayer.facade.configuration;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
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
    ConcurrentMap<String, List<PERule>> dependants(String uriId);

    /**
     * 转换器
     * 
     * @param uriId
     * @return
     */
    ConcurrentMap<String, List<PERule>> convertors(String uriId);

    /**
     * 验证器
     * 
     * @param uriId
     * @return
     */
    ConcurrentMap<String, List<PERule>> validators(String uriId);
}
