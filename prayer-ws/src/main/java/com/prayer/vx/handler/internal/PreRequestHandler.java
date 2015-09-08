package com.prayer.vx.handler.internal;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
/**
 * 
 * @author Lang
 *
 */
public class PreRequestHandler implements Handler<RoutingContext>{

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/**
	 * 
	 */
	@Override
	public void handle(final RoutingContext routingContext) {
		System.out.println("Pre: " + routingContext.request().path());
		routingContext.next();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
