package com.prayer.vertx.uca.sender;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Resources;
import com.prayer.vertx.uca.responder.FailureResponder;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class ErrorAtaghan {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param clazz
     * @param response
     */
    public static void send500Error(final Class<?> clazz, final HttpServerResponse response) {
        /** 1.500 Error **/
        final AbstractException error = new _500InternalServerErrorException(clazz);
        final Envelop envelop = Envelop.failure(error);
        /** 2.构造响应器 **/
        final Responder<Envelop> responder = singleton(FailureResponder.class);
        responder.reckonHeaders(response, envelop);
        /** 3.生成最终响应结果 **/
        response.end(responder.buildBody(envelop), Resources.ENCODING.name());
    }
    /**
     * 
     * @param clazz
     * @param response
     */
    public static void sendError(final Class<?> clazz, final HttpServerResponse response, final JsonObject data) {
        /** 1.500 Error **/
        final AbstractException error = new _500InternalServerErrorException(clazz);
        final Envelop envelop = Envelop.failure(error);
        /** 2.构造响应器 **/
        final Responder<Envelop> responder = singleton(FailureResponder.class);
        responder.reckonHeaders(response, envelop);
        /** 3.生成最终响应结果 **/
        response.end(responder.buildBody(envelop), Resources.ENCODING.name());
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ErrorAtaghan() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
