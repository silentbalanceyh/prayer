package com.prayer.configurator;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.util.web.Interruptor;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Route相关配置
 * 
 * @author Lang
 *
 */
@Guarded
public class RouteConfigurator {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteConfigurator.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigInstantor service;
    /** 获取唯一的Vertx引用 **/
    @NotNull
    private transient final Vertx vertxRef;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RouteConfigurator(@NotNull final Vertx vertxRef) {
        this.service = singleton(ConfigBllor.class);
        this.vertxRef = vertxRef;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取系统的Router表
     * 
     * @return
     */
    @PreValidateThis
    public Router getRouter() {
        // 1.从H2的Database中读取所有的Route信息
        final ConcurrentMap<String, List<PERoute>> retMap = this.service.routes();
        // 2.如果读取成功的情况
        final Router retRouter = Router.router(this.vertxRef);
        if (retMap.isEmpty()) {
            info(LOGGER, "[E-VX] No route has been found in H2 database !");
        } else {
            for (final List<PERoute> routeList : retMap.values()) {
                // 4.Sub Router调用
                for (final PERoute item : routeList) {
                    final Route route = this.configRouter(retRouter, item);
                    retRouter.getRoutes().add(route);
                }
            }
        }
        return retRouter;
    }
    // ~ Private Methods =====================================

    private Route configRouter(final Router router, final PERoute metadata) {
        // 2.初始化Route，设置Method
        Route route = initRoute(router, metadata);
        // 3.设置Order
        if (Constants.ORDER.NOT_SET != metadata.getOrder()) {
            route = route.order(metadata.getOrder());
        }
        // 4.设置MIME
        if (null != metadata.getConsumerMimes() && !metadata.getConsumerMimes().isEmpty()) {
            for (final String mime : metadata.getConsumerMimes()) {
                route = route.consumes(mime);
            }
        }
        // 5.设置Handler
        this.registerHandler(route, metadata);
        return route;
    }

    private Route initRoute(final Router router, final PERoute metadata) {
        Route route = null;
        // Fix: 暂时不修改表结构
        final String path = metadata.getParent() + metadata.getPath();
        switch (metadata.getMethod()) {
        case POST: {
            route = router.post(path);
        }
            break;
        case PUT: {
            route = router.put(path);
        }
            break;
        case DELETE: {
            route = router.delete(path);
        }
            break;
        default: {
            route = router.get(path);
        }
            break;
        }
        return route;
    }

    private void registerHandler(final Route route, final PERoute metadata) {
        try {
            // RequestHandler
            if (null != metadata.getRequestHandler()) {
                Interruptor.interruptClass(getClass(), metadata.getRequestHandler().getName(), "GlobalHandler");
                Interruptor.interruptImplements(getClass(), metadata.getRequestHandler().getName(), Handler.class);
                route.blockingHandler(instance(metadata.getRequestHandler()));
            }
            // FailureHandler
            if (null != metadata.getFailureHandler()) {
                Interruptor.interruptClass(getClass(), metadata.getFailureHandler().getName(), "ErrorHandler");
                Interruptor.interruptImplements(getClass(), metadata.getFailureHandler().getName(), ErrorHandler.class);
                route.blockingHandler(instance(metadata.getFailureHandler()));
            }
        } catch (AbstractWebException ex) {
            peError(LOGGER, ex);
        }
    }

    /*
     * private void logHandler(final PERoute metadata, final boolean failure) {
     * final String registeredUri = metadata.getParent() + metadata.getPath();
     * if (failure) { info(LOGGER, WebLogger.I_MSGH_FAILURE,
     * metadata.getFailureHandler(), registeredUri, metadata.getOrder()); } else
     * { info(LOGGER, WebLogger.I_MSGH_REQUEST, metadata.getMethod().toString(),
     * registeredUri, metadata.getOrder(), metadata.getRequestHandler()); } }
     */
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
