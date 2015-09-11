package com.prayer.vx.configurator;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractWebException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.uca.assistant.Interruptor;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.SharedData;
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
	/** Request GlobalHandler Message **/
	private static final String MSG_REQUEST_HANDLER = "[I-VX] [{0}] {1} Order:{2} -> Request Handler {3}.";
	/** Failure GlobalHandler Message **/
	private static final String MSG_FAILURE_HANDLER = "[I-VX] Failure Handler {0} has been registered to {1} with order: {2}.";
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ConfigService service;
	/** 获取唯一的Vertx引用 **/
	@NotNull
	private transient Vertx vertxRef;
	/** 共享数据 **/
	@NotNull
	private transient SharedData sharedData;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouteConfigurator(@NotNull final Vertx vertxRef) {
		this.service = singleton(ConfigSevImpl.class);
		this.vertxRef = vertxRef;
		this.sharedData = vertxRef.sharedData();
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
		Router router = Router.router(this.vertxRef);
		// 2.初始化Route，设置Method
		Route route = initRoute(router, metadata);
		// 3.设置Order
		if (Constants.VX_DF_ORDER != metadata.getOrder()) {
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
				this.logHandler(metadata, false);
			}
			// FailureHandler
			if (null != metadata.getFailureHandler()) {
				Interruptor.interruptClass(getClass(), metadata.getFailureHandler(), "ErrorHandler");
				Interruptor.interruptImplements(getClass(), metadata.getFailureHandler(), ErrorHandler.class);
				route.failureHandler(instance(metadata.getFailureHandler()));
				this.logHandler(metadata, true);
			}
		} catch (AbstractWebException ex) {
			info(LOGGER, "[E-VX] Handler setting met error. Error Message = " + ex.getErrorMessage());
		}
	}

	private void logHandler(final RouteModel metadata, final boolean failure) {
		if (failure) {
			info(LOGGER, MessageFormat.format(MSG_FAILURE_HANDLER, metadata.getFailureHandler(),
					metadata.getParent() + metadata.getPath(), metadata.getOrder()));
		} else {
			info(LOGGER, MessageFormat.format(MSG_REQUEST_HANDLER, metadata.getMethod().toString(),
					metadata.getParent() + metadata.getPath(), metadata.getOrder(), metadata.getRequestHandler()));
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
