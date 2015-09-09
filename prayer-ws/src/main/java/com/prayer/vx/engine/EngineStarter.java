package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractException;
import com.prayer.vx.configurator.VertxConfigurator;
import com.prayer.vx.handler.deploy.VertxClusterHandler;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.VertxFactory;

/**
 * Prayer启动器，整个Prayer Framework的入口程序
 * 
 * @author Lang
 *
 */
public class EngineStarter {
	// ~ Static Fields =======================================
	private static final String VX_NAME = "PRAYER";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param args
	 */
	public static void main(final String args[]) throws AbstractException {
		// 1.构造Vertx整体环境
		final VertxConfigurator configurator = singleton(VertxConfigurator.class);
		final VertxOptions options = configurator.getOptions(VX_NAME);
		final VertxFactory factory = configurator.getFactory();
		// 2.判断是普通环境还是Cluster环境
		if (options.isClustered()) {
			factory.clusteredVertx(options, VertxClusterHandler.create());
		} else {
			final Vertx vertx = factory.vertx(options);
			final VerticleDeployer deployer = new VerticleDeployer(vertx);
			deployer.deployVerticles();
		}
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
