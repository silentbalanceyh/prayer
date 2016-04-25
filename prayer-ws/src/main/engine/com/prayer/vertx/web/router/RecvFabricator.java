package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.handler.RequestAcceptor;

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
        /** 2.Acceptor **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.ACCEPTOR)).handler(this.buildAcceptor());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private RequestAcceptor buildAcceptor() {
        final RequestAcceptor acceptor = RequestAcceptor.create();
        // TODO: Vertx-Web 扩展Acceptor
        return acceptor;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
