package com.prayer.vertx.web.route;

import com.prayer.facade.vtx.route.HubFabricator;
import com.prayer.model.meta.vertx.PERoute;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class UriPathFabricator implements HubFabricator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void immitRoute(@NotNull final Route routeRef, @NotNull final PERoute entity) {
        /** 1.构建最终Path **/
        final String path = entity.getParent() + entity.getPath();
        final HttpMethod method = entity.getMethod();
        /** 2.设置Route基本信息 **/
        routeRef.path(path);
        routeRef.method(method);
        /** 3.设置Order信息 **/
        if(Integer.MIN_VALUE != entity.getOrder()){
            routeRef.order(entity.getOrder());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
