package com.prayer.vertx.web.router;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.facade.vtx.route.HubFabricator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.vertx.web.route.HandlerFabricator;
import com.prayer.vertx.web.route.MimeFabricator;
import com.prayer.vertx.web.route.UriPathFabricator;

import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RouterFabricator implements Fabricator {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterFabricator.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient ConfigInstantor instantor = singleton(ConfigBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void immitRouter(@NotNull final Router router) {
        try {
            /** 1.从系统中读取所有的Route **/
            final ConcurrentMap<String, List<PERoute>> routes = instantor.routes();
            /** 2.如果读取成功 **/
            if (null == routes || routes.isEmpty()) {
                // TODO: 读取失败
            } else {
                for (final List<PERoute> routeList : routes.values()) {
                    /** 3.遍历内部 **/
                    for (final PERoute route : routeList) {
                        /** 4.执行内部函数操作单个Route **/
                        this.immitRouter(router, route);
                    }
                }
            }
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void immitRouter(final Router router, final PERoute entity) throws AbstractException {
        /** 1.Route初始化 **/
        final Route route = router.route();
        /** 2.基本信息 **/
        HubFabricator fabricator = singleton(UriPathFabricator.class);
        fabricator.immitRoute(route, entity);
        /** 3.MIME信息 **/
        fabricator = singleton(MimeFabricator.class);
        fabricator.immitRoute(route, entity);
        /** 4.Handler信息 **/
        fabricator = singleton(HandlerFabricator.class);
        fabricator.immitRoute(route, entity);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
