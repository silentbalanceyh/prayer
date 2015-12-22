package com.prayer.vx.configurator;

import static com.prayer.util.Log.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Resources;

import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class VertxConfigurator { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxConfigurator.class);
    /** 一个EngineCluster中提供的Factory的实例 **/
    private static final VertxFactory FACTORY = new VertxFactoryImpl();
    /** **/
    private static final String VX_PREFIX = "vertx.";
    // ~ Instance Fields =====================================
    /** 读取全局vertx的属性配置 **/
    private transient final PropertyKit LOADER = new PropertyKit(VertxConfigurator.class, Resources.VX_CFG_FILE);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /**
     * 获取当前VertxFactory提供的Global Context
     * 
     * @return
     */
    @NotNull
    public VertxFactory getFactory() {
        return FACTORY;
    }
    /**
     * 
     * @return
     */
    @NotNull
    public String getActiveName(){
        return this.LOADER.getString("vertx.active");
    }

    /**
     * 获取Vertx的Options
     * 
     * @param name
     * @return
     */
    @NotNull
    public VertxOptions getOptions(final String name) {
        final VertxOptions options = new VertxOptions();
        if (null == this.LOADER) {
            error(LOGGER, "VertX property file has not been initialized successfully !");
        } else {
            // Pool Size
            options.setEventLoopPoolSize(this.LOADER.getInt(VX_PREFIX + name + ".pool.size.event.loop"));
            options.setWorkerPoolSize(this.LOADER.getInt(VX_PREFIX + name + ".pool.size.worker"));
            options.setInternalBlockingPoolSize(this.LOADER.getInt(VX_PREFIX + name + ".pool.size.internal.blocking"));
            // Cluster
            options.setClustered(this.LOADER.getBoolean(VX_PREFIX + name + ".cluster.enabled"));
            options.setClusterHost(this.LOADER.getString(VX_PREFIX + name + ".cluster.host"));
            options.setClusterPort(this.LOADER.getInt(VX_PREFIX + name + ".cluster.port"));
            // Ping
            options.setClusterPingInterval(this.LOADER.getLong(VX_PREFIX + name + ".cluster.ping.interval"));
            options.setClusterPingReplyInterval(this.LOADER.getLong(VX_PREFIX + name + ".cluster.ping.interval.reply"));
            // Blocked Thread Check Interval
            options.setBlockedThreadCheckInterval(
                    this.LOADER.getLong(VX_PREFIX + name + ".blocked.thread.check.internal"));
            options.setMaxEventLoopExecuteTime(this.LOADER.getLong(VX_PREFIX + name + ".execute.time.max.event.loop"));
            options.setMaxWorkerExecuteTime(this.LOADER.getLong(VX_PREFIX + name + ".execute.time.max.worker"));
            // HA
            options.setHAEnabled(this.LOADER.getBoolean(VX_PREFIX + name + ".ha.enabled"));
            options.setHAGroup(this.LOADER.getString(VX_PREFIX + name + ".ha.group"));
            options.setQuorumSize(this.LOADER.getInt(VX_PREFIX + name + ".quorum.size"));
            options.setWarningExceptionTime(this.LOADER.getLong(VX_PREFIX + name + ".warning.exception.time"));
        }
        return options;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
