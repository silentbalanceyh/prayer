package com.prayer.vx.configurator;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractVertXException;
import com.prayer.exception.vertx.HandlerInvalidException;
import com.prayer.exception.vertx.HandlerNotFoundException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.util.Instance;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
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
	private transient ConfigService service;
	/** 获取唯一的Vertx引用 **/
	@NotNull
	private transient Vertx vertxRef;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouteConfigurator(final Vertx vertxRef) {
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
		Router router = Router.router(this.vertxRef);
		Route route = null;
		// 2.设置HttpMethod
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
		// 3.设置Order
		if (Constants.VX_DEFAULT_ORDER != metadata.getOrder()) {
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
		try {
			// RequestHandler
			if (null != metadata.getRequestHandler()) {
				this.checkHandler(metadata.getRequestHandler());
				route.handler(instance(metadata.getRequestHandler()));
				info(LOGGER, "[I-VX] Handler " + metadata.getRequestHandler() + " has been registered to "
						+ metadata.getParent() + metadata.getPath() + " with order: " + metadata.getOrder());
			}
			// FailureHandler
			if (null != metadata.getFailureHandler()) {
				this.checkHandler(metadata.getFailureHandler());
				route.failureHandler(instance(metadata.getFailureHandler()));
			}
		} catch (AbstractVertXException ex) {
			info(LOGGER, "[E-VX] Handler setting met error. Error Message = " + ex.getErrorMessage());
		}
		return router;
	}

	private void checkHandler(final String className) throws AbstractVertXException {
		// 1.检查是否存在这个类
		Class<?> clazz = Instance.clazz(className);
		if (null == clazz) {
			info(LOGGER, "[E-VX] Handler class not found: " + className);
			throw new HandlerNotFoundException(getClass(), className);
		} else {
			// 2.递归检索接口
			final List<Class<?>> interfaces = Instance.interfaces(className);
			boolean flag = false;
			for (final Class<?> item : interfaces) {
				if (item == Handler.class) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				info(LOGGER, "[E-VX] Verticle class invalid ( Implementations ): " + className);
				throw new HandlerInvalidException(getClass(), className);
			}
		}

	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
