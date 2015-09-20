package com.prayer.vx.configurator;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Interruptor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractWebException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RouteModel;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Route相关配置
 * 
 * @author Lang
 *
 */
@Guarded
public class RouteConfigurator {
	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RouteConfigurator.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final ConfigService service;
	/** 获取唯一的Vertx引用 **/
	@NotNull
	private transient final Vertx vertxRef;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouteConfigurator(@NotNull final Vertx vertxRef) {
		this.service = singleton(ConfigSevImpl.class);
		this.vertxRef = vertxRef;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 获取系统的Router表
	 * 
	 * @return
	 */
	@PreValidateThis
	public ConcurrentMap<Router, String> getRouters() {
		// 1.从H2的Database中读取所有的Route信息
		final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = this.service.findRoutes();
		// 2.如果读取成功的情况
		final ConcurrentMap<Router, String> retRouters = new ConcurrentHashMap<>();
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			// 3.Sub Router
			result.getResult().values().forEach(routeList -> {
				// 4.Sub Router调用
				routeList.forEach(item -> {
					final Router router = this.configRouter(item);
					retRouters.put(router, item.getParent());
				});
			});
		} else {
			info(LOGGER, "[E-VX] No route has been found in H2 database !");
		}
		return retRouters;
	}
	// ~ Private Methods =====================================

	private Router configRouter(final RouteModel metadata) {
		// 1.创建Router
		final Router router = Router.router(this.vertxRef);
		// 2.初始化Route，设置Method
		final Route route = initRoute(router, metadata);
		// 3.设置Order
		if (Constants.ORDER.NOT_SET != metadata.getOrder()) {
			route.order(metadata.getOrder());
		}
		// 4.设置MIME
		if (null != metadata.getConsumerMimes() && !metadata.getConsumerMimes().isEmpty()) {
			for (final String mime : metadata.getConsumerMimes()) {
				route.consumes(mime);
			}
		}
		if (null != metadata.getProducerMimes() && !metadata.getProducerMimes().isEmpty()) {
			for (final String mime : metadata.getProducerMimes()) {
				route.produces(mime);
			}
		}
		// 5.设置Handler
		this.registerHandler(route, metadata);
		return router;
	}

	private Route initRoute(final Router router, final RouteModel metadata) {
		Route route = null;
		switch (metadata.getMethod()) {
		case POST: {
			route = router.post(metadata.getPath());
		}
			break;
		case PUT: {
			route = router.put(metadata.getPath());
		}
			break;
		case DELETE: {
			route = router.delete(metadata.getPath());
		}
			break;
		default: {
			route = router.get(metadata.getPath());
		}
			break;
		}
		return route;
	}

	private void registerHandler(final Route route, final RouteModel metadata) {
		try {
			// RequestHandler
			if (null != metadata.getRequestHandler()) {
				Interruptor.interruptClass(getClass(), metadata.getRequestHandler(), "GlobalHandler");
				Interruptor.interruptImplements(getClass(), metadata.getRequestHandler(), Handler.class);
				route.handler(instance(metadata.getRequestHandler()));
			}
			// FailureHandler
			if (null != metadata.getFailureHandler()) {
				Interruptor.interruptClass(getClass(), metadata.getFailureHandler(), "ErrorHandler");
				Interruptor.interruptImplements(getClass(), metadata.getFailureHandler(), ErrorHandler.class);
				route.failureHandler(instance(metadata.getFailureHandler()));
			}
		} catch (AbstractWebException ex) {
			error(LOGGER, WebLogger.E_HANDLER_ERROR, ex.getErrorMessage());
		}
	}

	/*
	 * private void logHandler(final RouteModel metadata, final boolean failure)
	 * { final String registeredUri = metadata.getParent() + metadata.getPath();
	 * if (failure) { info(LOGGER, WebLogger.I_MSGH_FAILURE,
	 * metadata.getFailureHandler(), registeredUri, metadata.getOrder()); } else
	 * { info(LOGGER, WebLogger.I_MSGH_REQUEST, metadata.getMethod().toString(),
	 * registeredUri, metadata.getOrder(), metadata.getRequestHandler()); } }
	 */
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
