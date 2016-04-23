package com.prayer.vertx.launcher;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.callback.CallbackClosurer;
import com.prayer.vertx.config.VertxOptsIntaker;
import com.prayer.vertx.util.RemoteRefers;

import io.vertx.core.VertxOptions;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public class VertxLauncher implements Launcher {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxLauncher.class);
    /** **/
    private static final Intaker<ConcurrentMap<String, VertxOptions>> INTAKER = singleton(VertxOptsIntaker.class);
    /** Active Vertx Name **/
    private static final String INSTANCE = "PRAYER-ENGINE";
    /** **/
    private static final String STOP_ADDR = MessageFormat.format(RmiKeys.VERTX_STOP, INSTANCE);

    // ~ Instance Fields =====================================
    /** **/
    private transient final Promulgator promulgator = singleton(SinglePromulgator.class, INSTANCE);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() throws AbstractException {
        /** 1.判断Meta Server是否在运行 **/
        if (Ensurer.running(getClass(), LOGGER)) {
            final VertxOptions options = this.initOpts();
            /** 2.先写入配置到RMI **/
            if (options.isClustered()) {
                /** 3.集群发布 **/
            } else {
                /** 3.单节点发布 **/
                promulgator.deploy(options);
            }
            /** 4.将Vertx的启动数据写入RMI **/
            final String address = MessageFormat.format(RmiKeys.VERTX_OPTS, INSTANCE);
            RemoteRefers.registry(address, options.toString());
            /** 5.开启新线程，处理Stop **/
            new Thread(new CallbackClosurer(STOP_ADDR, this::exit)).start();
        }
    }

    @Override
    public void stop() {
        /** 1.写入数据到STOP地址 **/
        RemoteRefers.registry(STOP_ADDR, String.valueOf(Boolean.TRUE));
        /** 2.退出 **/
        System.exit(0);
    }

    @Override
    public boolean running() {
        final String address = MessageFormat.format(RmiKeys.VERTX_OPTS, INSTANCE);
        return RemoteRefers.isRunning(address);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void exit() {
        try {
            this.promulgator.undeploy();
            System.out.println("Stopped...");
        } catch (AbstractException ex) {
            
        }
    }

    /** 初始化Vertx配置 **/
    private VertxOptions initOpts() throws AbstractException {
        final ConcurrentMap<String, VertxOptions> options = INTAKER.ingest();
        return options.get(INSTANCE);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
