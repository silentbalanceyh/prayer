package com.prayer.handler.web;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;
import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
	/** **/
	@NotNull
	private transient final ConfigService service;

	// ~ Static Block ========================================
	/** 创建方法 **/
	public static ServiceHandler create() {
		return new ServiceHandler();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ServiceHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, "[VX-I] Handler : " + getClass().getName() + ", Order : " + Constants.VX_OD_SERVICE);
		// 1.获取请求和相应信息
		final HttpServerRequest request = routingContext.request();
		final HttpServerResponse response = routingContext.response();
		// 2.从系统中读取URI接口规范
		final ServiceResult<UriModel> result = this.service.findUri(request.path(), request.method());
		final JsonObject params = routingContext.get(Constants.VX_CTX_PARAMS);
		if (ResponseCode.SUCCESS == result.getResponseCode() && null != params) {
			final UriModel uri = result.getResult();
			final Vertx vertx = routingContext.vertx();
			final EventBus bus = vertx.eventBus();
			// 业务逻辑层需要使用的Globa ID在发送前植入
			params.put(Constants.BUS_GLOBAL_ID,uri.getGlobalId());
			bus.send(uri.getAddress(), params, res -> {
				if (res.succeeded()) {
					// TODO: Response
					final JsonObject webRet = (JsonObject) res.result().body();
					response.end(webRet.encodePrettily());
				}
			});
		} else {
			// 500 Internal Server
			final AbstractWebException error = new InternalServerErrorException(getClass());
			final RestfulResult webRet = new RestfulResult(StatusCode.OK);
			webRet.setResponse(null, StatusCode.INTERNAL_SERVER_ERROR, error);
			response.setStatusCode(webRet.getStatusCode().status());
			response.setStatusMessage(webRet.getErrorMessage());
			// 触发错误信息
			info(LOGGER, "RestfulResult = " + webRet);
			routingContext.put(Constants.VX_CTX_ERROR, webRet);
			routingContext.fail(webRet.getStatusCode().status());
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
