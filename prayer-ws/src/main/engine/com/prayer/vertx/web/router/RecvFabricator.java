package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.web.handler.RequestAcceptor;
import com.prayer.vertx.web.handler.UriStrainer;

import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecvFabricator extends AbstractFabricator implements Fabricator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void immitRouter(@NotNull final Router router) {
        /** 1.URI解析 **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.URI)).handler(this.buildStrainer());
        /** 2.Acceptor **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.ACCEPTOR))
                .blockingHandler(this.buildAcceptor());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 执行URI的解析流程，过滤URI
     * 
     * @return
     */
    private UriStrainer buildStrainer() {
        final UriStrainer strainer = UriStrainer.create();
        // TODO: Vertx-Web 扩展URI解析器
        return strainer;
    }

    private RequestAcceptor buildAcceptor() {
        final RequestAcceptor acceptor = RequestAcceptor.create();
        // TODO: Vertx-Web 扩展Acceptor
        return acceptor;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
