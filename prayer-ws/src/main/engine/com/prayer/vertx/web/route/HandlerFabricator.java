package com.prayer.vertx.web.route;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.route.HubFabricator;
import com.prayer.model.meta.vertx.PERoute;

import io.vertx.ext.web.Route;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class HandlerFabricator implements HubFabricator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void immitRoute(@NotNull final Route routeRef, @NotNull final PERoute entity) {
        /** 1.设置Request Handler **/
        if (null != entity.getRequestHandler()) {
            routeRef.handler(singleton(entity.getRequestHandler()));
        }
        /** 2.设置Failure Handler **/
        if (null != entity.getFailureHandler()) {
            routeRef.failureHandler(singleton(entity.getFailureHandler()));
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
