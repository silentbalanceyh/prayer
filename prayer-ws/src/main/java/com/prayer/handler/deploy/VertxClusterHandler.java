package com.prayer.handler.deploy;

import static com.prayer.uca.assistant.WebLogger.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractException;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.vx.engine.VerticleDeployer;

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

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(VertxClusterHandler.class);
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
			final VerticleDeployer deployer = new VerticleDeployer(vertx);
			try {
				deployer.deployVerticles();
			} catch (AbstractException ex) {
				error(LOGGER, WebLogger.E_CLUSTER_ERROR, ex.getErrorMessage());
			}
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
