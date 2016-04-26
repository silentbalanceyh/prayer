package com.prayer.vertx.handler.standard;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.request.Alloter;
import com.prayer.vertx.web.dispatcher.NormalizeAlloter;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class RequestAcceptor implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Alloter nomalized = singleton(NormalizeAlloter.class);

    // ~ Static Block ========================================
    /** **/
    public static RequestAcceptor create() {
        return new RequestAcceptor();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(final RoutingContext event) {
        /** 1.直接运行 **/
        nomalized.accept(event, handler -> {
            final Envelop envelop = handler.result();
            System.out.println(envelop.result());
        });
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
