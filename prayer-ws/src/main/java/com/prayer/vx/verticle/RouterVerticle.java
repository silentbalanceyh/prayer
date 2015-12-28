package com.prayer.vx.verticle; // NOPMD

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import com.prayer.assistant.RouterInjector;
import com.prayer.handler.web.FailureHandler;
import com.prayer.handler.web.RouterHandler;
import com.prayer.handler.web.ServiceHandler;
import com.prayer.util.cv.Constants;
import com.prayer.vx.configurator.RouteConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 路由Verticle，负责前端的路由导航
 * 
 * @author Lang
 *
 */
@Guarded
public class RouterVerticle extends AbstractVerticle {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ServerConfigurator configurator;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RouterVerticle() {
        super();
        this.configurator = singleton(ServerConfigurator.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void start() {
        // 1.根据Options创建Server相关信息
        final HttpServer server = vertx.createHttpServer(this.configurator.getApiOptions());
        // 因为有Block Thread的问题，这里直接使用Blocking代码
        vertx.<Router> executeBlocking(future -> {
            // 2.先初始化Router
            final RouteConfigurator routeConfigurator = instance(RouteConfigurator.class.getName(), vertx);
            final Router router = routeConfigurator.getRouter();
            // 3.complete
            future.complete(router);

        } , result -> {
            if (result.succeeded()) {
                final Router router = result.result();
                // 4.根路径Router
                RouterInjector.injectWebDefault(router);
                // 5.AuthProvider创建
                RouterInjector.injectSecurity(router);

                // 6.最前端的URL处理
                injectStandard(router);
                // 7.监听端口
                server.requestHandler(router::accept).listen();
            }
        });
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectStandard(final Router router) {
        router.route(Constants.ROUTE.API).order(Constants.ORDER.ROUTER).handler(RouterHandler.create());
        // router.route(Constants.ROUTE.API).order(Constants.ORDER.VALIDATION).handler(ValidationHandler.create());
        // router.route(Constants.ROUTE.API).order(Constants.ORDER.CONVERTOR).handler(ConversionHandler.create());
        router.route(Constants.ROUTE.API).order(Constants.ORDER.SERVICE).handler(ServiceHandler.create());
        // 7.Failure处理器设置
        router.route(Constants.ROUTE.API).order(Constants.ORDER.FAILURE).failureHandler(FailureHandler.create());
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
