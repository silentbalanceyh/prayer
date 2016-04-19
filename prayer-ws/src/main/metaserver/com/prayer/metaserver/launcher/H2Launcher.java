package com.prayer.metaserver.launcher;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.OptionsIntaker;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.h2.H2OptionsIntaker;
import com.prayer.metaserver.h2.server.ClusterServer;
import com.prayer.metaserver.h2.server.SingleServer;
import com.prayer.resource.InceptBus;

/**
 * 
 * @author Lang
 *
 */
public class H2Launcher implements Launcher {
    // ~ Static Fields =======================================
    /** Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.MetaServer.class);
    /** H2 Options **/
    private static final OptionsIntaker INTAKER = singleton(H2OptionsIntaker.class);
    /** **/
    private static H2Server server = null;

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
        /** 1.读取Meta Server配置文件路径 **/
        final String configFile = INCEPTOR.getString(Point.MetaServer.CONFIG);
        /** 2.使用配置文件读取配置 **/
        final Options options = INTAKER.ingest(configFile);
        /** 3.使用配置初始化H2 Server **/
        if (this.isClustered(options)) {
            server = ClusterServer.create(options);
        } else {
            server = SingleServer.create(options);
        }
        /** 4.启动Server **/
        server.start();
    }

    /** 当前配置是否集群 **/
    private boolean isClustered(final Options options) {
        return options.readOpts().containsKey("cluster");
    }

    public static void main(String args[]) throws AbstractException {
        Launcher launcher = new H2Launcher();
        launcher.start();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
