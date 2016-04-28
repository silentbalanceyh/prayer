package com.prayer.facade.business.service;

import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonObject;

/**
 * ID发现接口，用于处理特殊情况下的identifier
 * @author Lang
 *
 */
public interface IdAnagnorisis {
    /**
     * 
     * @param params
     */
    void identifier(final JsonObject params) throws AbstractException;
}
