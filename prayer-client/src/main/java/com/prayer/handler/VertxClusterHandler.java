package com.prayer.handler;

import static com.prayer.util.Instance.singleton;

import com.prayer.server.VerticleDeployer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 发布过程的Handler
 * 
 * @author Lang
 *
 */
@Guarded
public class VertxClusterHandler implements Handler<AsyncResult<Vertx>> {

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 核心方法
	 */
	@Override
	public void handle(@NotNull final AsyncResult<Vertx> event) {
		if (event.succeeded()) {
			final Vertx vertx = event.result();
			final VerticleDeployer deployer = singleton(VerticleDeployer.class,vertx);
			deployer.deployVerticles();
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
