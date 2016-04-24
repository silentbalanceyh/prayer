package com.prayer.vertx.web;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;
import com.prayer.vertx.opts.ServerOptsIntaker;
import com.prayer.vertx.web.router.CorsFabricator;
import com.prayer.vertx.web.router.LocateFabricator;
import com.prayer.vertx.web.router.RecvFabricator;
import com.prayer.vertx.web.router.RouterFabricator;
import com.prayer.vertx.web.router.StdnFabricator;
import com.prayer.vertx.web.router.WebFabricator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import net.sf.oval.guard.Guarded;

/**
 * 路由Verticle，负责最前端导航
 * 
 * @author Lang
 *
 */
@Guarded
public class RouterAgent extends AbstractVerticle {
    // ~ Static Fields =======================================

    /** 读取器 **/
    protected static final Inceptor INCEPTOR = InceptBus.build(Point.Server.class);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterAgent.class);
    /** **/
    private static final Intaker<ConcurrentMap<Integer, HttpServerOptions>> INTAKER = singleton(
            ServerOptsIntaker.class);
    // ~ Instance Fields =====================================
    /** Router构造 **/
    private transient Fabricator fabricator = singleton(RouterFabricator.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 唯一模式，单件操作 **/
    public RouterAgent() {
        super();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() {
        try {
            /** 0.Http Server **/
            final ConcurrentMap<Integer,HttpServerOptions> configMap = INTAKER.ingest();
            final HttpServerOptions options = configMap.get(INCEPTOR.getInt("server.port.api"));
            final HttpServer server = vertx.createHttpServer(options);
            /** 1.读取Router引用，处理Block Thread的问题 **/
            vertx.<Router> executeBlocking(future -> {
                /** 2.初始化Router **/
                final Router router = Router.router(vertx);
                /** 3.初始化标准Router，注入方式操作 **/
                fabricator.immitRouter(router);
                /** 4.完成初始化 **/
                future.complete(router);
            }, result -> {
                if (result.succeeded()) {
                    final Router router = result.result();
                    /** 5.Api Default 配置 **/
                    this.injectRouter(router);
                    /** 6.Server监听 **/
                    routes(router);
                    server.requestHandler(router::accept).listen();
                }
            });
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            return;
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // TODO：Debug用，后期会Remove
    private void routes(final Router router){
        for(final Route route: router.getRoutes()){
            System.out.println(route);
        }
    }
    
    private void injectRouter(final Router router) {
        /** 1.Web专用 **/
        // Cookie
        // Body
        Fabricator fabricator = singleton(WebFabricator.class);
        fabricator.immitRouter(router);
        /** 2.Recv **/
        // URI Parser
        // Acceptor
        fabricator = singleton(RecvFabricator.class);
        fabricator.immitRouter(router);
        /** 3.Cors **/
        // Cors
        fabricator = singleton(CorsFabricator.class);
        fabricator.immitRouter(router);
        /** 4.Stdn **/
        // Data Strainer
        // Data Inspector
        // Stdner
        fabricator = singleton(StdnFabricator.class);
        fabricator.immitRouter(router);
        /** 5.Locate **/
        // Sender
        // Failure Handler
        fabricator = singleton(LocateFabricator.class);
        fabricator.immitRouter(router);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
