package com.prayer.facade.vtx.endpoint;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.vtx.uca.request.Responder;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

/**
 * Sender标准接口Asker
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface MessageAsker<T> extends Handler<AsyncResult<Message<T>>>{
    /**
     * **/
    Responder<T> getResponder();
}
