package com.prayer.facade.vtx;

import com.prayer.model.business.behavior.ActResponse;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface Channel {
    /**
     * 连接业务逻辑接口
     * 
     * @param data
     * @return
     */
    ActResponse invoke(final JsonObject data);
}
