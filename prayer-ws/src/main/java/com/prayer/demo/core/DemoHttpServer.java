package com.prayer.demo.core;

import io.vertx.core.AbstractVerticle;
/**
 * Type: Java Application
 * Main: io.vertx.core.Starter
 * Program Arguments: run com.prayer.demo.core.DemoHttpServer
 * @author Lang
 *
 */
public class DemoHttpServer extends AbstractVerticle {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	public void start() {
		vertx.createHttpServer().requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		}).listen(8080);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
