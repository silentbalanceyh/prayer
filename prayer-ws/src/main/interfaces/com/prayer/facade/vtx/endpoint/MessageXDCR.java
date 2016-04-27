package com.prayer.facade.vtx.endpoint;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface MessageXDCR extends Handler<AsyncResult<Message<JsonObject>>>{
    
}
