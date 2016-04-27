package com.prayer.vertx.handler.standard;

import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.request.Resolver;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.web.StatusCode;
import com.prayer.vertx.util.Fault;
import com.prayer.vertx.util.MimeParser;
import com.prayer.vertx.web.model.Envelop;
import com.prayer.vertx.web.model.Refiner;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public class RequestSinker implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Resolver resolver;

    /** **/
    public static RequestSinker create() {
        return new RequestSinker();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.初始化Resolver **/
        Envelop stumer = this.initResolver(event);
        /** 2.从上一个节点中抽取Envelop **/
        final Envelop envelop = event.get(WebKeys.Request.ENVP);
        if (Fault.route(event, stumer)) {
            /** 3.执行Flow **/
            stumer = injectRequest(event, envelop);
        } else {
            // Fix: Response Already Written
            return;
        }
        /** 如果flow中包含了异常信息则会有问题 **/
        if (Fault.route(event, stumer)) {
            /** 构造参数信息 **/
            this.injectUAC(event, envelop);
            event.next();
        } else {
            // Fix: Response Already Written
            return;
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectUAC(final RoutingContext context, final Envelop envelop) {
        /** SKIP设置 **/
        final JsonObject rawData = envelop.getRaw();
        final JsonArray validators = rawData.getJsonObject(context.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.VALIDATOR);
        context.put(WebKeys.Request.Rule.SKIPV, null == validators || validators.isEmpty());
        final JsonArray convertors = rawData.getJsonObject(context.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.CONVERTOR);
        context.put(WebKeys.Request.Rule.SKIPC, null == convertors || convertors.isEmpty());
        final JsonArray dependants = rawData.getJsonObject(context.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.DEPENDENT);
        context.put(WebKeys.Request.Rule.SKIPD, null == dependants || dependants.isEmpty());
    }

    private Envelop injectRequest(final RoutingContext context, final Envelop envelop) {
        Envelop stumer = Envelop.success();
        try {
            final PEUri uri = new PEUri(envelop.getUriData(context.request().method()));
            final JsonObject data = this.resolver.resolve(context, uri);
            if (null != data) {
                stumer = Envelop.success(data);
            } else {
                stumer = Envelop.failure(new _500InternalServerErrorException(getClass()));
            }
        } catch (AbstractWebException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }

    private Envelop initResolver(final RoutingContext event) {
        /** 3.提取HttpServerRequest **/
        final HttpServerRequest request = event.request();
        final String mime = MimeParser.content(request.headers());
        /** 4.构造Resolver **/
        Envelop envelop = Envelop.success();
        try {
            this.resolver = Refiner.resolve(mime);
        } catch (AbstractWebException ex) {
            envelop = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return envelop;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
