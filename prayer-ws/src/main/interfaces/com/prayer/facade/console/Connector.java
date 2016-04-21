package com.prayer.facade.console;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface Connector {
    /**
     * 连接专用接口
     * @return
     */
    boolean connecting();
    /**
     * 读取接口
     * @return
     */
    JsonObject read();
}
