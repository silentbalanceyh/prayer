package com.prayer.demo.starter;

import com.prayer.util.io.PropertyKit;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

@Guarded
public final class OptionsReader {
    // ~ Static Fields =======================================
    /** **/
    private static PropertyKit LOADER = new PropertyKit(SingleStarter.class, "/vertx.properties");
    /** Property Key Name Prefix **/
    private static String VX_PREFIX = "vertx.";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Verticle选项读取
     * 
     * @return
     */
    public static DeploymentOptions readOpts() {
        final DeploymentOptions options = new DeploymentOptions();
        options.setHa(true); // 默认是false
        options.setInstances(10); // 默认是1
        options.setWorker(false); // 默认是false，仅为Demo演示
        options.setMultiThreaded(false); // 默认是false，仅为Demo演示
        return options;
    }

    /**
     * 
     * @param name
     * @return
     */
    @NotNull
    public static VertxOptions readOpts(@NotNull @NotEmpty @NotBlank final String name) {
        final VertxOptions options = new VertxOptions();
        // Pool Size Configuration
        options.setEventLoopPoolSize(LOADER.getInt(VX_PREFIX + name + ".pool.size.event.loop"));
        options.setWorkerPoolSize(LOADER.getInt(VX_PREFIX + name + ".pool.size.worker"));
        options.setInternalBlockingPoolSize(LOADER.getInt(VX_PREFIX + name + ".pool.size.internal.blocking"));
        // Cluster Configuration Items
        options.setClustered(LOADER.getBoolean(VX_PREFIX + name + ".cluster.enabled"));
        options.setClusterHost(LOADER.getString(VX_PREFIX + name + ".cluster.host"));
        options.setClusterPort(LOADER.getInt(VX_PREFIX + name + ".cluster.port"));
        // Ping Time/Out Configuration
        options.setClusterPingInterval(LOADER.getLong(VX_PREFIX + name + ".cluster.ping.interval"));
        options.setClusterPingReplyInterval(LOADER.getLong(VX_PREFIX + name + ".cluster.ping.interval.reply"));
        // Blocked Thread Check Interval
        options.setBlockedThreadCheckInterval(LOADER.getLong(VX_PREFIX + name + ".blocked.thread.check.internal"));
        options.setMaxEventLoopExecuteTime(LOADER.getLong(VX_PREFIX + name + ".execute.time.max.event.loop"));
        options.setMaxWorkerExecuteTime(LOADER.getLong(VX_PREFIX + name + ".execute.time.max.worker"));
        // HA Configuration
        options.setHAEnabled(LOADER.getBoolean(VX_PREFIX + name + ".ha.enabled"));
        options.setHAGroup(LOADER.getString(VX_PREFIX + name + ".ha.group"));
        options.setQuorumSize(LOADER.getInt(VX_PREFIX + name + ".quorum.size"));
        options.setWarningExceptionTime(LOADER.getLong(VX_PREFIX + name + ".warning.exception.time"));
        return options;

    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private OptionsReader(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
