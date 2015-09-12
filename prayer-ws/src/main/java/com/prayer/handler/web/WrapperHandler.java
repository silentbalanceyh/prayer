package com.prayer.handler.web;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.uca.assistant.ErrGenerator;

import io.vertx.core.Handler;
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
public class WrapperHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(WrapperHandler.class);
	/** **/
	private static final String PARAM_ID = "identifier";
	/** **/
	private static final String PARAM_SCRIPT = "script";
	/** **/
	private static final String PARAM_DATA = "data";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** 创建方法 **/
	public static WrapperHandler create() {
		return new WrapperHandler();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, "[VX-I] Handler : " + getClass().getName() + ", Order : " + Constants.VX_OD_WRAPPER);
		// 2.从系统中读取URI面向业务层的规范
		final UriModel uri = routingContext.get(Constants.VX_CTX_URI);
		final JsonObject params = routingContext.get(Constants.VX_CTX_PARAMS);
		// 3.生成封装参数
		final JsonObject wrapper = new JsonObject();
		if (null != params && null != uri) {
			wrapper.put(PARAM_ID, uri.getGlobalId());
			wrapper.put(PARAM_SCRIPT, uri.getScript());
			wrapper.put(PARAM_DATA, params);
			routingContext.put(Constants.VX_CTX_PARAMS, wrapper);
			routingContext.next();
		} else {
			// 500 Internal Server
			final RestfulResult webRet = RestfulResult.create();
			ErrGenerator.error500(webRet, getClass());
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
