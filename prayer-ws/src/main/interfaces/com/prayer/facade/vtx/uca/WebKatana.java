package com.prayer.facade.vtx.uca;

import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;

/**
 * UCA的配置验证器
 * @author Lang
 *
 */
public interface WebKatana {
    /**
     * 验证参数本身信息
     * @param config
     * @param name
     * @throws AbstractWebException
     */
    void interrupt(JsonObject config, String name) throws AbstractWebException;
}
