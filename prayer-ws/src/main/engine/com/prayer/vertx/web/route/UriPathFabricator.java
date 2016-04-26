package com.prayer.vertx.web.route;

import com.prayer.facade.constant.Symbol;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.HubFabricator;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.resource.InceptBus;

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
    /** Order Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Web.class, Point.Web.HANDLER_ORDERS);
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
        /** 3.如果是Pattern这种，执行过滤，让该路由支持所有的方法 **/
        if(path.indexOf(Symbol.COLON) < 0){
            // 非Pattern的URI直接Route路由
            routeRef.method(method);
        }else{
            // 如果包含了Pattern，Order刚好在Pattern上的这个Route不设置Method
            final int order = INCEPTOR.getInt(Point.Web.Orders.Api.URI);
            if(order != entity.getOrder()){
                routeRef.method(method);
            }
        }
        /** 4.设置Order信息 **/
        if(Integer.MIN_VALUE != entity.getOrder()){
            routeRef.order(entity.getOrder());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
