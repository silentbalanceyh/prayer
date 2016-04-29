package com.prayer.vertx.handler.standard;

import com.google.common.net.HttpHeaders;
import com.prayer.resource.Resources;
import com.prayer.util.vertx.Fault;
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
        final Envelop envelop = Fault.get(event);
        /** 2.设置相应信息 **/
        final HttpServerResponse response = event.response();
        /** 3.编码方式 **/
        /** 4.从Envelop中抽取响应所需详细信息 **/
        response.setStatusCode(envelop.status().code());
        response.setStatusMessage(envelop.status().message());
        response.headers().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + Resources.ENCODING);
        response.end(envelop.result().encode(), Resources.ENCODING.name());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
