package com.prayer.handler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;

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
		final JsonObject paramMap = routingContext.get(Constants.VX_CTX_PARAMS);
		System.out.println(paramMap.encodePrettily());
		routingContext.next();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
