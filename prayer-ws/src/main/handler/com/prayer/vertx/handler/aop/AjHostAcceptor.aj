package com.prayer.vertx.handler.aop;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.uca.headers.Acceptor;
import com.prayer.util.vertx.Feature;
import com.prayer.vertx.uca.headers.HostAcceptor;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】最前端Host头部信息检查
 * 
 * @author Lang
 *
 */
public aspect AjHostAcceptor {
    // ~ Point Cut ===========================================
    /** 切点定义 **/
    pointcut HostPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.RequestAcceptor.handle(RoutingContext)) && args(event) && target(Handler);

    // ~ Point Cut Implements ================================
    /** 切点实现 **/
    before(final RoutingContext event):HostPointCut(event){
        /** **/
        final Acceptor acceptor = singleton(HostAcceptor.class);
        final HttpServerRequest request = event.request();
        final Envelop envelop = acceptor.accept(request);
        /** AOP-ROUTE：直接路由 **/
        Feature.route(event, envelop);
    }
}
