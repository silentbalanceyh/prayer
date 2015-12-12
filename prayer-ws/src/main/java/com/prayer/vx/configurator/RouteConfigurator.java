package com.prayer.vx.configurator;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Interruptor;
import com.prayer.assistant.WebLogger;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RouteModel;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;

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
    private transient final ConfigService service;
    /** 获取唯一的Vertx引用 **/
    @NotNull
    private transient final Vertx vertxRef;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RouteConfigurator(@NotNull final Vertx vertxRef) {
        this.service = singleton(ConfigSevImpl.class);
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
        final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = this.service.findRoutes();
        // 2.如果读取成功的情况
        final Router retRouter = Router.router(this.vertxRef);
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            // 3.Sub Router
            result.getResult().values().forEach(routeList -> {
                // 4.Sub Router调用
                routeList.forEach(item -> {
                    final Route route = this.configRouter(retRouter, item);
                    retRouter.getRoutes().add(route);
                });
            });
        } else {
            info(LOGGER, "[E-VX] No route has been found in H2 database !");
        }
        return retRouter;
    }
    // ~ Private Methods =====================================

    private Route configRouter(final Router router, final RouteModel metadata) {
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
        /* 
         * Producer会影响Match的流程
         * if (null != metadata.getProducerMimes() &&
         * !metadata.getProducerMimes().isEmpty()) { for (final String mime :
         * metadata.getProducerMimes()) { info(LOGGER, "[I-WEB] Produce -> " +
         * mime + " : " + route); route = route.produces(mime); } }
         */
        // 5.设置Handler
        this.registerHandler(route, metadata);
        return route;
    }

    private Route initRoute(final Router router, final RouteModel metadata) {
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

    private void registerHandler(final Route route, final RouteModel metadata) {
        try {
            // RequestHandler
            if (null != metadata.getRequestHandler()) {
                Interruptor.interruptClass(getClass(), metadata.getRequestHandler(), "GlobalHandler");
                Interruptor.interruptImplements(getClass(), metadata.getRequestHandler(), Handler.class);
                route.handler(instance(metadata.getRequestHandler()));
            }
            // FailureHandler
            if (null != metadata.getFailureHandler()) {
                Interruptor.interruptClass(getClass(), metadata.getFailureHandler(), "ErrorHandler");
                Interruptor.interruptImplements(getClass(), metadata.getFailureHandler(), ErrorHandler.class);
                route.failureHandler(instance(metadata.getFailureHandler()));
            }
        } catch (AbstractWebException ex) {
            error(LOGGER, WebLogger.E_HANDLER_ERROR, ex.getErrorMessage());
        }
    }

    /*
     * private void logHandler(final RouteModel metadata, final boolean failure)
     * { final String registeredUri = metadata.getParent() + metadata.getPath();
     * if (failure) { info(LOGGER, WebLogger.I_MSGH_FAILURE,
     * metadata.getFailureHandler(), registeredUri, metadata.getOrder()); } else
     * { info(LOGGER, WebLogger.I_MSGH_REQUEST, metadata.getMethod().toString(),
     * registeredUri, metadata.getOrder(), metadata.getRequestHandler()); } }
     */
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
