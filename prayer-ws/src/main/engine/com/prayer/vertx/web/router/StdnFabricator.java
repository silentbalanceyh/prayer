package com.prayer.vertx.web.router;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.vertx.handler.DataInspector;
import com.prayer.vertx.handler.DataStrainer;
import com.prayer.vertx.handler.RequestStdner;

import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class StdnFabricator extends AbstractFabricator implements Fabricator {

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
        /** Data Inspector **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.INSPECTOR)).blockingHandler(this.buildInspector());
        /** Data Strainer **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.DATA)).blockingHandler(this.buildStrainer());
        /** Stdner **/
        router.route(PATH_API).order(orders().getInt(Point.Web.Orders.Api.STDN)).blockingHandler(this.buildStdner());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private DataInspector buildInspector() {
        final DataInspector inspector = DataInspector.create();
        // TODO: Vertx-Web：可扩展验证器Inspector
        return inspector;
    }

    private DataStrainer buildStrainer() {
        final DataStrainer strainer = DataStrainer.create();
        // TODO：Vertx-Web：可扩展过滤器Strainer
        return strainer;
    }

    private RequestStdner buildStdner() {
        final RequestStdner stdner = RequestStdner.create();
        // TODO：Vertx-Web：可扩展Web规范器STDN
        return stdner;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
