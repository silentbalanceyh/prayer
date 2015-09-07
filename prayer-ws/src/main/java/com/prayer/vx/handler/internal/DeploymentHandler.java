package com.prayer.vx.handler.internal;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * 
 * @author Lang
 *
 */
public class DeploymentHandler implements Handler<AsyncResult<String>> {
	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentHandler.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 注意DeploymentHandler和ClusterHandler的单件模式不同，因为Deployment是针对Verticle的，
	 * 所以不需要使用单件模式
	 * 
	 * @return
	 */
	public static DeploymentHandler create() {
		return new DeploymentHandler();
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * Handler的核心方法
	 */
	@Override
	public void handle(AsyncResult<String> event) {
		if (event.succeeded()) {
			final String ret = event.result();
			info(LOGGER,"[I-VX] Deploy verticle successfully ! Result = " + ret);
		} else {
			info(LOGGER,"[E-VX] Deploy verticle met error ! Result = " + event.result());
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
