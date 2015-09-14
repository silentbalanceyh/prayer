package com.prayer.vx.verticle;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.SecurityMode;
import com.prayer.handler.security.BasicAuthHandler;
import com.prayer.handler.web.ConversionHandler;
import com.prayer.handler.web.FailureHandler;
import com.prayer.handler.web.RouterHandler;
import com.prayer.handler.web.ServiceHandler;
import com.prayer.handler.web.ValidationHandler;
import com.prayer.handler.web.WrapperHandler;
import com.prayer.vx.configurator.RouteConfigurator;
import com.prayer.vx.configurator.SecurityConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
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
	private transient final ServerConfigurator configurator;
	/** **/
	@NotNull
	private transient final SecurityConfigurator securitor;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouterVerticle() {
		super();
		this.configurator = singleton(ServerConfigurator.class);
		this.securitor = singleton(SecurityConfigurator.class);
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
		injectWebDefault(router);

		// 4.AuthProvider创建
		injectSecurity(router);

		// 5.Session的使用设置
		injectSession(router);

		// 6.最前端的URL处理
		injectStandard(router);

		// 7.设置Sub Router
		subRouters.forEach((subRouter, value) -> {
			router.mountSubRouter(value, subRouter);
		});

		// 8.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void injectSecurity(final Router router) {
		final AuthProvider authProvider = this.securitor.getProvider();
		router.route().handler(UserSessionHandler.create(authProvider));
		if (SecurityMode.BASIC == this.securitor.getMode()) {
			router.route(Constants.VX_AUTH_ROOT).order(Constants.VX_OD_AUTH)
					.handler(BasicAuthHandler.create(authProvider));
		}
	}

	private void injectWebDefault(final Router router) {
		router.route().order(Constants.VX_OD_COOKIE).handler(CookieHandler.create());
		router.route().order(Constants.VX_OD_BODY).handler(BodyHandler.create());
		router.route().order(Constants.VX_OD_CORS).handler(CorsHandler.create("*"));
	}

	private void injectStandard(final Router router) {
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_ROUTER).handler(RouterHandler.create());
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_VALIDATION).handler(ValidationHandler.create());
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_CONVERTOR).handler(ConversionHandler.create());
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_SERVICE).handler(ServiceHandler.create());
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_WRAPPER).handler(WrapperHandler.create());
		// 7.Failure处理器设置
		router.route(Constants.VX_URL_ROOT).order(Constants.VX_OD_FAILURE).failureHandler(FailureHandler.create());
	}

	private void injectSession(final Router router) {
		if (vertx.isClustered()) {
			router.route().order(Constants.VX_OD_SESSION)
					.handler(SessionHandler.create(ClusteredSessionStore.create(vertx)));
		} else {
			router.route().order(Constants.VX_OD_SESSION)
					.handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
