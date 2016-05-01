package com.prayer.fantasm.vtx.uca;

import com.prayer.exception.web._400HeaderKeyMissedException;
import com.prayer.model.web.StatusCode;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

/**
 * Acceptor的父类
 * 
 * @author Lang
 *
 */
public abstract class AbstractAcceptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 子类调用，通用验证当前头是否存在 **/
    protected Envelop acceptRequired(final HttpServerRequest request, final String headerKey) {
        /** 1.读取头信息 **/
        final MultiMap headers = request.headers();
        /** 2.判断Headers **/
        Envelop envelop = null;
        if (headers.contains(headerKey)) {
            envelop = Envelop.success(new JsonObject());
        } else {
            envelop = Envelop.failure(new _400HeaderKeyMissedException(getClass(), headerKey, request.path()),
                    StatusCode.BAD_REQUEST);
        }
        return envelop;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
