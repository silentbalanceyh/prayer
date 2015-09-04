package com.prayer.vx.engine;

import com.prayer.exception.AbstractException;

import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;

/**
 * Prayer启动器，整个Prayer Framework的入口程序
 * @author Lang
 *
 */
public class EngineStarter {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param args
	 */
	public static void main(final String args[]) throws AbstractException{
		// 1.构造Vertx整体环境
		final VertxFactory factory = new VertxFactoryImpl();
		final Vertx vertx = factory.vertx();
		// 2.使用Vertx发布配置好的Verticle
		final EngineDeployer deployer = new EngineDeployer(vertx);
		deployer.deploySyncVerticles();
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
