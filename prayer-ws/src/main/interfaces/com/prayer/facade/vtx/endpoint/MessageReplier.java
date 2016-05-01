package com.prayer.facade.vtx.endpoint;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * Consumer标准接口Replier
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface MessageReplier<T> extends Handler<Message<JsonObject>>{

}
