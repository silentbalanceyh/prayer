package com.prayer.verticle.standard;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.configurator.ServerConfigurator;
import com.prayer.constant.Constants;
import com.prayer.constant.web.WebConfig;
import com.prayer.handler.web.SharedLoginHandler;
import com.prayer.handler.web.SharedLogoutHandler;
import com.prayer.handler.web.UserLoggedHandler;
import com.prayer.util.web.RouterInjector;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Web 应用管理平台
 * 
 * @author Lang
 *
 */
@Guarded
public class WebVerticle extends AbstractVerticle {
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
    public WebVerticle() {
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
        final HttpServer server = vertx.createHttpServer(this.configurator.getWebOptions());

        // 2.Web Default
        final Router router = Router.router(vertx);
        // 3.首页跳转
        router.route("/").order(Constants.ORDER.NOT_SET).handler(context -> {
            context.response().putHeader("location", WebConfig.WEB_ACT_LOGIN_PAGE).setStatusCode(302).end();
        });
        RouterInjector.injectWebDefault(router);

        // 3.Static静态资源
        RouterInjector.injectStatic(router, this.configurator.getEndPoint());

        // 4.Session的使用设置
        RouterInjector.injectSession(vertx, router);

        // 5.预处理的Handler
        injectLogged(router);

        // 6.监听Cluster端口
        server.requestHandler(router::accept).listen();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectLogged(final Router router) {
        router.route(WebConfig.WEB_DYC_ROUTE_ADMIN).order(Constants.ORDER.WEB.SHARED)
                .handler(SharedLoginHandler.create());
        router.route(WebConfig.WEB_ACT_LOGOUT).order(Constants.ORDER.WEB.LOGOUT)
                .handler(SharedLogoutHandler.create());
        // router.route(Constants.WEB.DYNAMIC_OPTIONS).order(Constants.ORDER.OD_OPTIONS).handler(OptionsHandler.create());
        router.route(WebConfig.WEB_DYC_ROUTE_ADMIN).order(Constants.ORDER.WEB.OD_PROFILE)
                .handler(UserLoggedHandler.create());
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
