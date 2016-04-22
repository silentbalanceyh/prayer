package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.template.TplBuilder;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.StaticHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Session以及UserSession构造器器
 * 
 * @author Lang
 *
 */
@Guarded
public class StaticFabricator extends AbstractFabricator implements Fabricator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 将静态的Route注入到Router中 **/
    @Override
    public void immitRouter(@NotNull final Router router) {
        /** Static Handler **/
        router.route(PATH_RES).order(orders().getInt(Point.Web.Orders.STATIC)).handler(this.buildStatic());
        router.route(PATH_RES).order(orders().getInt(Point.Web.Orders.STATIC)).failureHandler(this.buildError());
        /** Favicon Handler **/
        router.route(PATH_FAVICON).order(orders().getInt(Point.Web.Orders.FAVICON)).handler(this.buildFavicon());
        /** Template Handler **/
        router.route(PATH_DYADM).order(orders().getInt(Point.Web.Orders.TPLMODE)).handler(TplBuilder.build(TPL_MODE));
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 创建Favicon Handler
     * 
     * @return
     */
    private FaviconHandler buildFavicon() {
        final FaviconHandler handler = FaviconHandler.create();
        // TODO: Vertx-Web：可扩展Favicon Handler
        return handler;
    }

    /**
     * 创建Static Handler
     * 
     * @return
     */
    private StaticHandler buildStatic() {
        final StaticHandler handler = StaticHandler.create();
        // TODO：Vertx-Web：可扩展构造Static Handler
        handler.setCachingEnabled(false);
        return handler;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
