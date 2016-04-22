package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class WebFabricator extends AbstractFabricator implements Fabricator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 将Web默认的Route注入到Router中 **/
    @Override
    public void immitRouter(@NotNull final Router router) {
        /** Cookie Handler **/
        router.route().order(orders().getInt(Point.Web.Orders.COOKIE)).handler(this.buildCookie());
        /** Body Handler **/
        router.route().order(orders().getInt(Point.Web.Orders.BODY)).handler(this.buildBody());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 创建Cors Handler
     * 
     * @return
     */
    private CookieHandler buildCookie() {
        final CookieHandler handler = CookieHandler.create();
        // TODO: Vertx-Web：可扩展Cookie Handler
        return handler;
    }

    private BodyHandler buildBody() {
        final BodyHandler handler = BodyHandler.create();
        // TODO: Vertx-Web：可扩展Body Handler
        return handler;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
