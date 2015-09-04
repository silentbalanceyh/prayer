package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractException;

import io.vertx.core.Vertx;

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
		final EngineCluster cluster = singleton(EngineCluster.class);
		final Vertx vertx = cluster.getVertx("PRAYER");
		// 2.使用Vertx发布配置部署的Verticle
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
