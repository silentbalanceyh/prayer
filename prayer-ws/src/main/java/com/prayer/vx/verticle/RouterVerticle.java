package com.prayer.vx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * 路由Verticle，负责前端的路由导航
 * 
 * @author Lang
 *
 */
public class RouterVerticle extends AbstractVerticle {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void start() {
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route("/test").handler(routingContext -> {

			// This handler will be called for every request
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/plain");

			// Write to the response and end it
			response.end("Hello World Router from Vert.x-Web!");
		});

		server.requestHandler(router::accept).listen(8080);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
