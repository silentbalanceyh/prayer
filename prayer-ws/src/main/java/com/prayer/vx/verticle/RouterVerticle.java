package com.prayer.vx.verticle; // NOPMD

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.assistant.RouterInjector;
import com.prayer.constant.Constants;
import com.prayer.handler.web.ConversionHandler;
import com.prayer.handler.web.FailureHandler;
import com.prayer.handler.web.RouterHandler;
import com.prayer.handler.web.ServiceHandler;
import com.prayer.handler.web.ValidationHandler;
import com.prayer.handler.web.WrapperHandler;
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

		// 2.根路径Router
		final Router router = Router.router(vertx);
		RouterInjector.injectWebDefault(router);

		// 3.AuthProvider创建
		RouterInjector.injectSecurity(router);

		// 4.Session的使用设置
		RouterInjector.injectSession(vertx, router);

		// 5.最前端的URL处理
		injectStandard(router);

		// 6.设置Sub Router
		final RouteConfigurator routeConfigurator = singleton(RouteConfigurator.class, vertx);
		final ConcurrentMap<Router, String> subRouters = routeConfigurator.getRouters();
		subRouters.forEach((subRouter, value) -> {
			router.mountSubRouter(value, subRouter);
		});

		// 7.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void injectStandard(final Router router) {
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_ROUTER).handler(RouterHandler.create());
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_VALIDATION).handler(ValidationHandler.create());
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_CONVERTOR).handler(ConversionHandler.create());
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_SERVICE).handler(ServiceHandler.create());
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_WRAPPER).handler(WrapperHandler.create());
		// 7.Failure处理器设置
		router.route(Constants.VX_API_ROOT).order(Constants.VX_OD_FAILURE).failureHandler(FailureHandler.create());
	}

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
