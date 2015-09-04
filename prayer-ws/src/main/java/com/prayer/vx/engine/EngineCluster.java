package com.prayer.vx.engine;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class EngineCluster {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(EngineCluster.class);
	/** 一个EngineCluster中提供的Factory的实例 **/
	private static final VertxFactory FACTORY = new VertxFactoryImpl();
	// ~ Instance Fields =====================================
	/** 读取全局vertx的属性配置 **/
	private transient final PropertyKit LOADER = new PropertyKit(EngineCluster.class, Resources.VX_CFG_FILE);

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 获取Vertx相关信息
	 * 
	 * @return
	 */
	@NotNull
	public Vertx getVertx(@NotNull @NotBlank @NotEmpty final String name) {
		Vertx vertx = null;
		if (null != FACTORY) {
			vertx = FACTORY.vertx(this.getOptions(name));
		}
		return vertx;
	}

	/**
	 * 获取当前VertxFactory提供的Global Context
	 * 
	 * @return
	 */
	@NotNull
	public Context getContext() {
		Context context = null;
		if (null != FACTORY) {
			context = FACTORY.context();
		}
		return context;
	}

	// ~ Private Methods =====================================
	private VertxOptions getOptions(final String name) {
		VertxOptions options = new VertxOptions();
		if (null != this.LOADER) {
			// Pool Size
			options.setEventLoopPoolSize(this.LOADER.getInt("vertx." + name + ".pool.size.event.loop"));
			options.setWorkerPoolSize(this.LOADER.getInt("vertx." + name + ".pool.size.worker"));
			options.setInternalBlockingPoolSize(this.LOADER.getInt("vertx." + name + ".pool.size.internal.blocking"));
			// Cluster
			options.setClustered(this.LOADER.getBoolean("vertx." + name + ".cluster.enabled"));
			options.setClusterHost(this.LOADER.getString("vertx." + name + ".cluster.host"));
			options.setClusterPort(this.LOADER.getInt("vertx." + name + ".cluster.port"));
			// Ping
			options.setClusterPingInterval(this.LOADER.getLong("vertx." + name + ".cluster.ping.interval"));
			options.setClusterPingReplyInterval(this.LOADER.getLong("vertx." + name + ".cluster.ping.interval.reply"));
			// Blocked Thread Check Interval
			options.setBlockedThreadCheckInterval(
					this.LOADER.getLong("vertx." + name + ".blocked.thread.check.internal"));
			options.setMaxEventLoopExecuteTime(this.LOADER.getLong("vertx." + name + ".execute.time.max.event.loop"));
			options.setMaxWorkerExecuteTime(this.LOADER.getLong("vertx." + name + ".execute.time.max.worker"));
			// HA
			options.setHAEnabled(this.LOADER.getBoolean("vertx." + name + ".ha.enabled"));
			options.setHAGroup(this.LOADER.getString("vertx." + name + ".ha.group"));
			options.setQuorumSize(this.LOADER.getInt("vertx." + name + ".quorum.size"));
			options.setWarningExceptionTime(this.LOADER.getLong("vertx." + name + ".warning.exception.time"));
		} else {
			info(LOGGER, "[I-VX] VertX property file has not been initialized successfully !");
		}
		return options;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
