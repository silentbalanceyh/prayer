package com.prayer.vxv.standard;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.vx.configurator.RouteConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
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
	private transient ServerConfigurator configurator;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouterVerticle() {
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
		// 2.从H2 Database中读取所有的Route信息
		final RouteConfigurator routeConfigurator = singleton(RouteConfigurator.class,vertx);
		final ConcurrentMap<Router, String> subRouters = routeConfigurator.getRouters();
		// 3.根路径Router
		final Router router = Router.router(vertx);
		router.route().handler(CookieHandler.create());
		router.route().handler(BodyHandler.create());
		// 4.设置Sub Router
		for(final Router subRouter: subRouters.keySet()){
			final String parent = subRouters.get(subRouter);
			router.mountSubRouter(parent, subRouter);
		}
		// 5.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
