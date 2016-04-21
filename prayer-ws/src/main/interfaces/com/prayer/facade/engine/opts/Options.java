package com.prayer.facade.engine.opts;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractLauncherException;

import io.vertx.core.json.JsonObject;

/**
 * 启动参数接口
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Options {
    /**
     * 读取启动参数，JsonObject格式
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject readOpts();
    /**
     * 当前Options是否合法
     * @return
     */
    @VertexApi(Api.READ)
    AbstractLauncherException getError();
}
