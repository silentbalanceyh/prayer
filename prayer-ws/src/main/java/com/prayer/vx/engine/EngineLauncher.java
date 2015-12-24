package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.hazelcast.config.Config;
import com.prayer.base.exception.AbstractException;
import com.prayer.handler.deploy.VertxClusterHandler;
import com.prayer.util.StringKit;
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
    // ~ Instance Fields =====================================
    /** **/
    private transient final H2DatabaseServer server = singleton(H2DatabaseServer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 创建启动器
     * 
     * @return
     */
    public static EngineLauncher create() {
        return new EngineLauncher();
    }

    /**
     * 
     * @param args
     */
    public static void main(final String... args) throws AbstractException {
        final EngineLauncher launcher = new EngineLauncher();
        launcher.runTool(args);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 按照目录Deploy元数据信息
     * 
     * @param args
     * @return
     * @throws AbstractException
     */
    public boolean deployData(final String dataFolder) throws AbstractException {
        boolean flag = false;
        if (StringKit.isNil(dataFolder)) {
            flag = server.initMetadata();
        } else {
            flag = server.initMetadata(dataFolder);
        }
        return flag;
    }

    /**
     * 元数据运行接口
     * 
     * @param args
     * @return
     * @throws AbstractException
     */
    public boolean startDatabase(final String... args) throws AbstractException {
        return server.start();
    }

    /**
     * 开启Vert.X Engine
     * 
     * @param args
     * @throws AbstractException
     */
    public void startEngine(final String... args) throws AbstractException {
        // 1.构造Vertx整体环境
        final VertxConfigurator configurator = singleton(VertxConfigurator.class);
        final VertxOptions options = configurator.getOptions(configurator.getActiveName());
        final VertxFactory factory = configurator.getFactory();
        // 2.判断是普通环境还是Cluster环境
        if (options.isClustered()) {
            final Config config = singleton(Config.class);
            final ClusterManager mgr = singleton(HazelcastClusterManager.class, config);
            options.setClusterManager(mgr);
            factory.clusteredVertx(options, singleton(VertxClusterHandler.class));
        } else {
            final Vertx vertx = factory.vertx(options);
            final VerticleDeployer deployer = singleton(VerticleDeployer.class, vertx);
            deployer.deployVerticles();
        }
    }

    /**
     * OOB的运行接口
     * 
     * @param args
     * @throws AbstractException
     */
    public void runTool(final String... args) throws AbstractException {
        if (startDatabase(args)) {
            // OOB 数据的Deploy过程
            boolean ret = this.deployData(null);
            if (ret) {
                this.startEngine(args);
            }
        }
    }

    // ~ Private Methods =====================================
    private EngineLauncher() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
