package com.prayer.client;

import static com.prayer.util.Instance.singleton;

import com.hazelcast.config.Config;
import com.prayer.handler.VertxClusterHandler;
import com.prayer.server.VerticleDeployer;
import com.prayer.server.VertxConfigurator;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.VertxFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * Prayer启动器，整个Prayer Framework的入口程序
 * 
 * @author Lang
 *
 */
public final class ClientLauncher {
	// ~ Static Fields =======================================

	private static final String VX_NAME = "PRAYER-CLIENT";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param args
	 */
	public static void main(final String... args) {
		// 1.构造Vertx整体环境
		final VertxConfigurator configurator = singleton(VertxConfigurator.class);
		final VertxOptions options = configurator.getOptions(VX_NAME);
		final VertxFactory factory = configurator.getFactory();
		// 2.判断是否是Cluster环境
		if(options.isClustered()){
			final Config hazelcastConfig = singleton(Config.class);
			final ClusterManager mgr = singleton(HazelcastClusterManager.class, hazelcastConfig);
			options.setClusterManager(mgr);
			factory.clusteredVertx(options, singleton(VertxClusterHandler.class));
		}else{
			final Vertx vertx = factory.vertx(options);
			final VerticleDeployer deployer = singleton(VerticleDeployer.class,vertx);
			deployer.deployVerticles();
		}
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private ClientLauncher() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
