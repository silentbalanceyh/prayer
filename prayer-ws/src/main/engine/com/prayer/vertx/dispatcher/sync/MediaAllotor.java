package com.prayer.vertx.dispatcher.sync;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.headers.Acceptor;
import com.prayer.facade.vtx.request.Allotor;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.headers.ContentTypeAcceptor;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 同步模式即可，位于Normalize之后，可传入params
 * 
 * @author Lang
 *
 */
public class MediaAllotor implements Allotor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Acceptor ctAcceptor = singleton(ContentTypeAcceptor.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop accept(final RoutingContext context, final JsonObject params) {
        /** 1.使用上一步检测的结果读取PEUri **/
        final HttpServerRequest request = context.request();
        final PEUri entity = this.buildEntity(request.method(), params);
        /** 2.验证Content-Type **/
        Envelop envelop = ctAcceptor.accept(request, entity.getContentMimes());
        return envelop;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private PEUri buildEntity(final HttpMethod method, final JsonObject params) {
        final JsonObject data = params.getJsonObject(WebKeys.Envelop.DATA).getJsonObject(WebKeys.Envelop.Data.BODY);
        return new PEUri(data);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
