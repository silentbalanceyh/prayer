package com.prayer.metaserver.launcher;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.metaserver.MetaOptsIntaker;
import com.prayer.facade.metaserver.h2.H2Server;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.h2.config.H2OptionsIntaker;
import com.prayer.metaserver.h2.server.ClusterServer;
import com.prayer.metaserver.h2.server.SingleServer;

/**
 * 
 * @author Lang
 *
 */
public class H2Launcher implements Launcher {
    // ~ Static Fields =======================================
    /** H2 Options **/
    private static final MetaOptsIntaker INTAKER = singleton(H2OptionsIntaker.class);

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2Launcher.class);
    /** **/
    private static H2Server SERVER = null;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 启动方法 **/
    @Override
    public void start() throws AbstractException {
        if (!running()) {
            /** 1.Server配置初始化 **/
            SERVER = this.getServer();
            /** 2.启动Server **/
            SERVER.start();
        }
    }

    /** 停止方法 **/
    @Override
    public void stop() throws AbstractException {
        /** 1.Server配置初始化 **/
        SERVER = this.getServer();
        /** 2.停止Server **/
        SERVER.stop();
    }

    /** 是否在运行 **/
    @Override
    public boolean running() {
        boolean running = false;
        try {
            running = Ensurer.running(getClass(), LOGGER);
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
        return running;
    }

    private H2Server getServer() throws AbstractException {
        H2Server server = null;
        /** 2.使用配置文件读取配置 **/
        final Options options = INTAKER.ingest();
        /** 3.使用配置初始化H2 Server **/
        if (this.isClustered(options)) {
            server = ClusterServer.create(options);
        } else {
            server = SingleServer.create(options);
        }
        return server;
    }

    /** 当前配置是否集群 **/
    private boolean isClustered(final Options options) {
        return options.readOpts().containsKey("cluster");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
