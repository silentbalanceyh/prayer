package com.prayer.vx.verticle;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.vx.configurator.RouteConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;
import com.prayer.vx.handler.web.FailureHandler;
import com.prayer.vx.handler.web.RouterHandler;
import com.prayer.vx.handler.web.ValidationHandler;
import com.prayer.vx.sec.OAuth2Provider;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.ext.web.sstore.LocalSessionStore;
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
		final RouteConfigurator routeConfigurator = singleton(RouteConfigurator.class, vertx);
		final ConcurrentMap<Router, String> subRouters = routeConfigurator.getRouters();

		// 3.根路径Router
		final Router router = Router.router(vertx);
		router.route().order(Constants.VX_OD_COOKIE).handler(CookieHandler.create());
		router.route().order(Constants.VX_OD_BODY).handler(BodyHandler.create());

		// 4.AuthProvider创建
		final AuthProvider authProvider = OAuth2Provider.create(vertx);
		router.route().handler(UserSessionHandler.create(authProvider));

		// 5.Session的使用设置
		if (vertx.isClustered()) {
			router.route().order(Constants.VX_OD_SESSION)
					.handler(SessionHandler.create(ClusteredSessionStore.create(vertx)));
		} else {
			router.route().order(Constants.VX_OD_SESSION)
					.handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		}

		// 6.最前端的URL处理
		router.route("/*").order(Constants.VX_OD_ROUTER).handler(new RouterHandler());
		router.route("/*").order(Constants.VX_OD_VALIDATION).handler(new ValidationHandler());
		
		// 7.Failure处理器设置
		router.route("/*").failureHandler(new FailureHandler());

		// 8.设置Sub Router
		subRouters.forEach((subRouter, value) -> {
			router.mountSubRouter(value, subRouter);
		});

		// 9.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
