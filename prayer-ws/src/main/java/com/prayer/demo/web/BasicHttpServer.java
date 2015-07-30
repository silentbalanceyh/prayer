package com.prayer.demo.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
/**
 * 
 * @author Lang
 *
 */
public class BasicHttpServer extends AbstractVerticle{
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	public void start(){
		HttpServer server = vertx.createHttpServer();

		server.requestHandler(request -> {

		  // This handler gets called for each request that arrives on the server
		  HttpServerResponse response = request.response();
		  response.putHeader("content-type", "text/plain");

		  // Write to the response and end it
		  response.end("Hello World!");
		});

		server.listen(8080);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
