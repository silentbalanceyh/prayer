package com.prayer.vx.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class TestHandler implements Handler<RoutingContext> {

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void handle(RoutingContext routingContext) {
		System.out.println(routingContext.request().path());
		// // This handler will be called for every request
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "text/plain");
		// Write to the response and end it
		response.setChunked(true);
		response.write("Route1");
		response.end();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
