package com.prayer.vertx.handler.standard;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 这里需要初始化请求操作了
 * @author Lang
 *
 */
public class RequestStdner implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static RequestStdner create() {
        return new RequestStdner();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        // TODO Auto-generated method stub
        final Envelop stumer = event.get(WebKeys.Request.Data.PARAMS);
        final PEUri uri = event.get(WebKeys.Request.Data.Meta.PEURI);
        System.out.println(stumer.result().encodePrettily());
        System.out.println(uri.toJson().encodePrettily());
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
