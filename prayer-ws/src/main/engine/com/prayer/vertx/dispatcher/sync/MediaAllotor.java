package com.prayer.vertx.dispatcher.sync;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.vtx.headers.Acceptor;
import com.prayer.facade.vtx.request.Allotor;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.headers.AcceptAcceptor;
import com.prayer.vertx.headers.ContentTypeAcceptor;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
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
    /** **/
    private transient Acceptor acAcceptor = singleton(AcceptAcceptor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop accept(final RoutingContext context, final Envelop params) {
        /** 1.使用上一步检测的结果读取PEUri **/
        final HttpServerRequest request = context.request();
        final HttpMethod method = request.method();
        final PEUri entity = new PEUri(params.getUriData(method));
        /** 2.验证Content-Type **/
        Envelop stumer = ctAcceptor.accept(request, entity.getContentMimes().toArray(Constants.T_STR_ARR));
        if (stumer.succeeded()) {
            /** 3.验证客户端偏好 Accept **/
            stumer = acAcceptor.accept(request, entity.getAcceptMimes().toArray(Constants.T_STR_ARR));
        }
        return stumer;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
