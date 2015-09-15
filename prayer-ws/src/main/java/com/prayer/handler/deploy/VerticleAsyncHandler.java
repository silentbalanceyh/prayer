package com.prayer.handler.deploy;

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.uca.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.uca.assistant.WebLogger;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VerticleAsyncHandler implements Handler<AsyncResult<String>> {
	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(VerticleAsyncHandler.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * Handler的核心方法
	 */
	@Override
	public void handle(@NotNull final AsyncResult<String> event) {
		final String ret = event.result();
		if (event.succeeded()) {
			info(LOGGER, WebLogger.I_VERTICLE_INFO, ret);
		} else {
			error(LOGGER, WebLogger.E_VERTICLE_ERROR, ret);
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
