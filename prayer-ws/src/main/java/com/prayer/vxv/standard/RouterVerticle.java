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
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.vx.engine.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
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
		HttpServer server = vertx.createHttpServer(this.configurator.getOptions());
		// 2.读取对应的Route信息
		final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = this.service.findRoutes();
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final ConcurrentMap<String, List<RouteModel>> routes = result.getResult();
			for (final String route : routes.keySet()) {
				final Router rootRouter = Router.router(vertx);
				// 当前Route下的地址
				final List<RouteModel> routeList = routes.get(route);
				for (final RouteModel routeItem : routeList) {
					final Router subRouter = Router.router(vertx);
					// 根据配置处理Route信息
					switch (routeItem.getMethod()) {
					case GET: {
						Handler<RoutingContext> requestHandler = instance(routeItem.getRequestHandler());
						subRouter.route(routeItem.getPath()).handler(requestHandler);
					}
						break;
					default:
						break;
					}
					// 挂载
					System.out.println(routeItem.getParent() + routeItem.getPath());
					rootRouter.mountSubRouter(routeItem.getParent(), subRouter);
					server.requestHandler(rootRouter::accept);
				}
			}
		} else {
			info(LOGGER, "[I-VX] Routes could not be found !");
		}
		server.listen();
		// server.listen();
		// server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
