package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.hazelcast.config.Config;
import com.prayer.exception.AbstractException;
import com.prayer.handler.deploy.VertxClusterHandler;
import com.prayer.vx.configurator.VertxConfigurator;

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
public final class EngineLauncher {
	// ~ Static Fields =======================================
	/** **/
	private static final String VX_NAME = "PRAYER";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param args
	 */
	public static void main(final String... args) throws AbstractException {
		// 1.H2数据库集成，并且发布H2的Metadata数据
		final H2DatabaseServer server = singleton(H2DatabaseServer.class);
		if (server.start()) {
			boolean ret = server.checkLocks();
			if (!ret) {
				// 1.初始化元数据
				ret = server.initMetadata();
				// 2.创建锁文件
				ret = ret && server.createLocks();
			}
			if (ret) {
				// 2.构造Vertx整体环境
				final VertxConfigurator configurator = singleton(VertxConfigurator.class);
				final VertxOptions options = configurator.getOptions(VX_NAME);
				final VertxFactory factory = configurator.getFactory();
				// 3.判断是普通环境还是Cluster环境
				if (options.isClustered()) {
					final Config hazelcastConfig = new Config();
					final ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
					options.setClusterManager(mgr);
					factory.clusteredVertx(options, VertxClusterHandler.create());
				} else {
					final Vertx vertx = factory.vertx(options);
					final VerticleDeployer deployer = new VerticleDeployer(vertx);
					deployer.deployVerticles();
				}
			}
		}
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private EngineLauncher() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
