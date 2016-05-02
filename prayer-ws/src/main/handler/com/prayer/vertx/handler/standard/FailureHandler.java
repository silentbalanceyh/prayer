package com.prayer.vertx.handler.standard;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.resource.Resources;
import com.prayer.util.vertx.Feature;
import com.prayer.vertx.uca.responder.FailureResponder;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class FailureHandler implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static FailureHandler create() {
        return new FailureHandler();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(final RoutingContext event) {
        /** 1.直接从event中读取Envelop **/
        final Envelop envelop = Feature.get(event);
        /** 2.设置相应信息 **/
        final HttpServerResponse response = event.response();
        /** 3.构造响应器 **/
        final Responder<Envelop> responder = singleton(FailureResponder.class);
        responder.reckonHeaders(response, envelop);
        /** 4.输出响应信息 **/
        response.end(responder.buildBody(envelop), Resources.ENCODING.name());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
