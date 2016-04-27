package com.prayer.vertx.handler.standard;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class DataInspector implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static DataInspector create() {
        return new DataInspector();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.跳跃中断 **/
        final Boolean skip = event.get(WebKeys.Request.Rule.SKIPV);
        if (skip) {
            /** 2.跳过不执行验证 **/
            event.next();
        } else {
            /** 3.验证处理流程，先读取Envelop **/
            final Envelop envelop = event.get(WebKeys.Request.ENVP);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
