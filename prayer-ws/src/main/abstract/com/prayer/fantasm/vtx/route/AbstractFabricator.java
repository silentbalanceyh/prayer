package com.prayer.fantasm.vtx.route;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractFabricator {
    // ~ Static Fields =======================================
    /** Web Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Web.class);
    /** Order Inceptor **/
    private static final Inceptor ORDIPTOR = InceptBus.build(Point.Web.class, Point.Web.HANDLER_ORDERS);
    /** Security Inceptor **/
    private static final Inceptor SECIPTOR = InceptBus.build(Point.Security.class);

    // ~ Protected Fields ====================================
    /** Template Mode **/
    protected static final String TPL_MODE = INCEPTOR.getString(Point.Web.Static.TEMPLATE_MODE);
    // ~ Static Fabricator
    /** Favicon Path **/
    protected static final String PATH_FAVICON = INCEPTOR.getString(Point.Web.Static.FAVICON);
    /** Resource Path **/
    protected static final String PATH_RES = INCEPTOR.getString(Point.Web.Static.RESOURCE);

    // ~ Dynamic Fabricator
    /** Dynamic Admin Path **/
    protected static final String PATH_DYADM = INCEPTOR.getString(Point.Web.Dynamic.ADMIN_ROUTE);
    /** Dynamic Path **/
    protected static final String PATH_DYNAMIC = INCEPTOR.getString(Point.Web.Dynamic.BASIC_ROUTE);

    // ~ Api Fabricator
    /** Api Path **/
    protected static final String PATH_API = INCEPTOR.getString(Point.Web.Api.PUBLIC);
    /** Secure Api Path **/
    protected static final String PATH_SEC_API = INCEPTOR.getString(Point.Web.Api.SECURE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** Web专用 **/
    protected Inceptor inceptor() {
        return INCEPTOR;
    }

    /** Order专用 **/
    protected Inceptor orders() {
        return ORDIPTOR;
    }

    /** Security专用 **/
    protected Inceptor securitor() {
        return SECIPTOR;
    }

    /**
     * 创建Error Handler
     * 
     * @return
     */
    protected Handler<RoutingContext> buildError() {
        final ErrorHandler handler = ErrorHandler.create();
        // TODO: Vertx-Web：可扩展Error Handler
        return handler;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
