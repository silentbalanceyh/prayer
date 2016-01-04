package com.prayer.verticle.standard; // NOPMD

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import com.prayer.configurator.RouteConfigurator;
import com.prayer.configurator.ServerConfigurator;
import com.prayer.constant.Constants;
import com.prayer.constant.web.WebConfig;
import com.prayer.handler.route.FailureHandler;
import com.prayer.handler.route.RouterHandler;
import com.prayer.handler.route.ServiceHandler;
import com.prayer.util.web.RouterInjector;

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
        router.route(WebConfig.WEB_API).order(Constants.ORDER.ENG.ROUTER).blockingHandler(RouterHandler.create());
        router.route(WebConfig.WEB_API).last().blockingHandler(ServiceHandler.create());
        // 7.Failure处理器设置
        router.route(WebConfig.WEB_API).order(Constants.ORDER.FAILURE).failureHandler(FailureHandler.create());
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
