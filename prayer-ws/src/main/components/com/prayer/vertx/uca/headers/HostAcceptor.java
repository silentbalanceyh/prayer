package com.prayer.vertx.uca.headers;

import com.prayer.facade.vtx.uca.headers.Acceptor;
import com.prayer.fantasm.vtx.uca.AbstractAcceptor;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;

/**
 * 
 * @author Lang
 *
 */
public class HostAcceptor extends AbstractAcceptor implements Acceptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop accept(final HttpServerRequest request, final String... expectes) {
        /** 1.直接检查Host头是否存在 **/
        return this.acceptRequired(request, HttpHeaders.HOST.toString());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
