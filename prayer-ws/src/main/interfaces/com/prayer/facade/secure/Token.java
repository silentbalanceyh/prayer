package com.prayer.facade.secure;

import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonObject;

/**
 * Token必备方法
 * @author Lang
 *
 */
public interface Token {
    /**
     * Token是否读取到
     * @return
     */
    boolean obtained();
    /**
     * Token中所有的信息数据
     * @return
     */
    JsonObject getCredential();
    /**
     * Token中如果有Error则读取Error
     * @return
     */
    AbstractException getError();
}
