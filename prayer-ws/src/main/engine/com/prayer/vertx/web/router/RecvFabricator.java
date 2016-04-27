package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.handler.standard.RequestAcceptor;
import com.prayer.vertx.handler.standard.RequestSinker;

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
    /** **/
    @Override
    public void immitRouter(@NotNull final Router router) {
        /** 1.Acceptor **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.ACCEPTOR)).handler(this.buildAcceptor());
        /** 2.Sinker **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.SINKER)).handler(this.buildSinker());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private RequestSinker buildSinker(){
        final RequestSinker sinker = RequestSinker.create();
        // TODO：Vertx-Web 扩展Sinker
        return sinker;
    }
    
    private RequestAcceptor buildAcceptor() {
        final RequestAcceptor acceptor = RequestAcceptor.create();
        // TODO: Vertx-Web 扩展Acceptor
        return acceptor;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
