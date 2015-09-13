package com.prayer.handler.web;

import static com.prayer.uca.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.handler.message.RecordSender;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.uca.assistant.HttpErrHandler;
import com.prayer.uca.assistant.WebLogger;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServiceHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** 创建方法 **/
	public static ServiceHandler create() {
		return new ServiceHandler();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.VX_OD_SERVICE);
		// 1.获取请求和相应信息
		final HttpServerResponse response = routingContext.response();
		// 2.从系统中读取URI接口规范
		final UriModel uri = routingContext.get(Constants.VX_CTX_URI);
		final JsonObject params = routingContext.get(Constants.VX_CTX_PARAMS);
		if (null != uri && null != params) {
			final Vertx vertx = routingContext.vertx();
			final EventBus bus = vertx.eventBus();
			// 发送Message到Event Bus
			bus.send(uri.getAddress(), params, RecordSender.create(response));
		} else {
			// 500 Internal Server
			HttpErrHandler.handle500Error(getClass(), routingContext);
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
