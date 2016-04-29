package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.handler.standard.FailureHandler;
import com.prayer.vertx.handler.standard.MessageLocator;

import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class LocateFabricator extends AbstractFabricator implements Fabricator {
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
        /** Service **/
        router.route(PATH_API).last().blockingHandler(this.buildSender());
        /** Failure **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.FAILURE)).failureHandler(this.buildFailure());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private MessageLocator buildSender() {
        final MessageLocator sender = MessageLocator.create();
        // TODO：Vertx-Web定义Failure
        return sender;
    }

    private FailureHandler buildFailure() {
        final FailureHandler handler = FailureHandler.create();
        // TODO：Vertx-Web定义Failure
        return handler;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
