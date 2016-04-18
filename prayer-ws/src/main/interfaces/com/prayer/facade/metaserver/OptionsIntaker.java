package com.prayer.facade.metaserver;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.web.configuration.HubOptions;

/**
 * 公共接口，主要用于配置数据读取操作
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface OptionsIntaker {
    /**
     * 读取默认配置的接口
     * 
     * @return
     */
    @VertexApi(Api.READ)
    HubOptions ingest(String file);
}
