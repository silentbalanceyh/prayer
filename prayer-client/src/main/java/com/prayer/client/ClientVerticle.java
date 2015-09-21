package com.prayer.client;

import static com.prayer.util.Instance.singleton;

import com.prayer.constant.Constants;
import com.prayer.server.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.ext.web.sstore.LocalSessionStore;
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
public class ClientVerticle extends AbstractVerticle {
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
    public ClientVerticle() {
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
        final HttpServer server = vertx.createHttpServer(this.configurator.getOptions());

        // 2.Web Default
        final Router router = Router.router(vertx);

        // 3.预处理的Handler
        injectWeb(router);

        // 4.Session处理
        injectSession(vertx, router);

        // 4.监听Cluster端口
        server.requestHandler(router::accept).listen();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectWeb(final Router router) {
        router.route().order(Constants.COOKIE).handler(CookieHandler.create());
        router.route().order(Constants.BODY).handler(BodyHandler.create());
        router.route().order(Constants.STATIC).handler(StaticHandler.create());
    }

    public static void injectSession(final Vertx vertx, final Router router) {
        SessionHandler handler = SessionHandler.create(ClusteredSessionStore.create(vertx));
        if (vertx.isClustered()) {
            handler = SessionHandler.create(ClusteredSessionStore.create(vertx));
        } else {
            handler = SessionHandler.create(LocalSessionStore.create(vertx));
        }
        router.route().order(Constants.SESSION).handler(handler);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
