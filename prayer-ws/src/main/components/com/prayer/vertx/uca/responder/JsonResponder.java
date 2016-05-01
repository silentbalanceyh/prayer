package com.prayer.vertx.uca.responder;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.resource.Resources;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class JsonResponder implements Responder<JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** Headers **/
    @Override
    public void reckonHeaders(@NotNull final HttpServerResponse response, @NotNull final JsonObject data) {
        /** 1.状态代码设置 **/
        final JsonObject status = this.getStatus(data);
        response.setStatusCode(status.getInteger(WebKeys.Envelop.Status.CODE));
        response.setStatusMessage(status.getString(WebKeys.Envelop.Status.MESSAGE));
        /** 2.响应头的处理 **/
        response.headers().add(HttpHeaders.CONTENT_TYPE, Resources.CONTENT_TYPE);
    }

    /** Body **/
    @Override
    public String buildBody(@NotNull final JsonObject data) {
        return data.encode();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonObject getStatus(final JsonObject data) {
        return data.getJsonObject(WebKeys.Envelop.STATUS);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
