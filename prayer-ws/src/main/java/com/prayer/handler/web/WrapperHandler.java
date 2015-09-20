package com.prayer.handler.web;

import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.HttpErrHandler;
import com.prayer.assistant.WebLogger;
import com.prayer.constant.Constants;
import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
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
	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.WRAPPER);
		// 2.从系统中读取URI面向业务层的规范
		final UriModel uri = routingContext.get(Constants.KEY.CTX_URI);
		final JsonObject params = routingContext.get(Constants.KEY.CTX_PARAMS);
		// 3.生成封装参数
		final JsonObject wrapper = new JsonObject();
		if (null == params || null == uri) {
			// 500 Internal Server
			HttpErrHandler.handle500Error(getClass(), routingContext);
		} else {
			final Session session = routingContext.session();
			// 无状态操作时session为null
			if(null != session){
				wrapper.put(Constants.PARAM.SESSION, session.id());
			}
			wrapper.put(Constants.PARAM.ID, uri.getGlobalId());
			wrapper.put(Constants.PARAM.SCRIPT, uri.getScript());
			wrapper.put(Constants.PARAM.METHOD, uri.getMethod().toString());
			wrapper.put(Constants.PARAM.DATA, params);
			wrapper.put(Constants.PARAM.FILTERS, uri.getReturnFilters());
			routingContext.put(Constants.KEY.CTX_PARAMS, wrapper);
			routingContext.next();
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
