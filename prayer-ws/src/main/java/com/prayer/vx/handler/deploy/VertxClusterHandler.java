package com.prayer.vx.handler.deploy;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractException;
import com.prayer.vx.engine.VerticleDeployer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * 发布过程的Handler
 * @author Lang
 *
 */
public class VertxClusterHandler implements Handler<AsyncResult<Vertx>>{

	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(VertxClusterHandler.class);
	/**
	 * 单件实例
	 */
	private static VertxClusterHandler HANDLER = null;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** 
	 * 单件模式
	 * @return
	 */
	public static VertxClusterHandler create(){
		if(null == HANDLER){
			HANDLER = new VertxClusterHandler();
		}
		return HANDLER;
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 核心方法
	 */
	@Override
	public void handle(final AsyncResult<Vertx> event){
		if(event.succeeded()){
			final Vertx vertx = event.result();
			final VerticleDeployer deployer = new VerticleDeployer(vertx);
			try{
				deployer.deployVerticles();
			}catch(AbstractException ex){
				info(LOGGER,"[E-VX] Cluster vertx met error !",ex);
			}
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
