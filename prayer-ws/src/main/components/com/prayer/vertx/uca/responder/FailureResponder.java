package com.prayer.vertx.uca.responder;

import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.resource.Resources;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 根据Envelop来构造响应结果
 * 
 * @author Lang
 *
 */
@Guarded
public class FailureResponder implements Responder<Envelop> {
    /**
     * 计算响应头
     **/
    @Override
    public void reckonHeaders(@NotNull final HttpServerResponse response, @NotNull final Envelop data) {
        /** 1.状态代码设置 **/
        response.setStatusCode(data.status().code());
        response.setStatusMessage(data.status().message());
        /** 2.响应头的处理 **/
        response.headers().add(HttpHeaders.CONTENT_TYPE, Resources.CONTENT_TYPE);
    }

    /**
     * 错误Body的构造
     */
    @Override
    public String buildBody(@NotNull final Envelop data) {
        return data.result().encode();
    }
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
