package com.prayer.vxv.standard;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.SystemEnum;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
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
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RouterVerticle.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ServerConfigurator configurator;
	/** **/
	@NotNull
	private transient ConfigService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouterVerticle() {
		this.configurator = singleton(ServerConfigurator.class);
		this.service = singleton(ConfigSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void start() {
		// 1.根据Options创建Server相关信息
		HttpServer server = vertx.createHttpServer(this.configurator.getOptions());
		// 2.从H2 Database中读取所有的Route信息
		final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = this.service.findRoutes();
		// 3.读取成功的情况
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			// 4.生成相应数据：Parent Routing -> Sub Routing ( List )
			final ConcurrentMap<String, List<RouteModel>> routes = result.getResult();
			// 5.每一组Parent生成一个rootRouter
			final Router router = Router.router(vertx);
			// 5.1 -> 创建Web需要使用的各种Handler
			router.route().handler(CookieHandler.create());
			router.route().handler(BodyHandler.create());

			for (final String route : routes.keySet()) {
				// 6.每一组Parent的Router下会有一个Sub Router列表
				final List<RouteModel> routeList = routes.get(route);
				// 7.设置每一个Parent下的Router信息
				for (final RouteModel item : routeList) {
					final Router subRouter = this.configRouter(item);
					router.mountSubRouter(item.getParent(), subRouter);
					server.requestHandler(router::accept);
				}
			}
			server.listen();
		} else {
			info(LOGGER, "[I-VX] Routes could not be found !");
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private Router configRouter(final RouteModel route) {
		// 1.创建Router
		Router retRouter = null;
		// 2.根据配置数据分发Router
		if (SystemEnum.HttpMethod.GET == route.getMethod()) {
			retRouter = this.configGet(route);
		} else {

		}
		return retRouter;
	}

	private Router configGet(final RouteModel route) {
		final Router retRouter = Router.router(vertx);
		final Handler<RoutingContext> requestHandler = instance(route.getRequestHandler());
		retRouter.get(route.getPath()).handler(requestHandler);
		return retRouter;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
