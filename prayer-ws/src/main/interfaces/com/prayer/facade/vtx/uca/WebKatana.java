package com.prayer.facade.vtx.uca;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;

/**
 * UCA的配置验证器
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface WebKatana {
    /**
     * 验证参数本身信息
     * @param config
     * @param name
     * @throws AbstractWebException
     */
    @VertexApi(Api.TOOL)
    void interrupt(JsonObject config, String name) throws AbstractWebException;
}
