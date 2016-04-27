package com.prayer.vertx.handler.standard;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.request.Allotor;
import com.prayer.facade.vtx.request.Asynchor;
import com.prayer.vertx.dispatcher.async.NormalizeAsynchor;
import com.prayer.vertx.dispatcher.sync.MediaAllotor;
import com.prayer.vertx.util.Fault;
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
    private transient Asynchor nomalized = singleton(NormalizeAsynchor.class);
    /** **/
    private transient Allotor media = singleton(MediaAllotor.class);

    // ~ Static Block ========================================
    /** **/
    public static RequestAcceptor create() {
        return new RequestAcceptor();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.直接运行 **/
        nomalized.accept(event, handler -> {
            Envelop envelop = handler.result();
            /** 出现了404，405，500错误 **/
            if (Fault.route(event, envelop)) {
                /** 2.检查媒体类型 **/
                envelop = media.accept(event, envelop.result());
            }
            /** 出现了415，406类型 **/
            if (Fault.route(event, envelop)) {

            }
            /** 没有出现任何Fault路由 **/
            event.next();
        });
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
