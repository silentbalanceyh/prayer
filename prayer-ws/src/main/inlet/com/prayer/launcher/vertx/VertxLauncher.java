package com.prayer.launcher.vertx;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.business.schema.MilieuBllor;
import com.prayer.facade.business.instantor.schema.EnvInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Injections;
import com.prayer.util.vertx.RemoteRefers;
import com.prayer.vertx.callback.VertxClosurer;
import com.prayer.vertx.config.VertxOptsIntaker;

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
    /** **/
    private static final String RUNNING = "RUNNING";

    // ~ Instance Fields =====================================
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
            /** 2.准备基础环境 **/
            this.prepareEnv();
            /** 3.发布组件 **/
            this.deployEnv(options);
            /** 4.最终完成 **/
            this.finalizeEnv(options);
        }
    }

    @Override
    public void stop() {
        /** 1.写入数据到STOP地址 **/
        RemoteRefers.registry(STOP_ADDR, Constants.EMPTY_STR);
        /** 关闭信息的打印 **/
        info(LOGGER, MessageFormat.format(MsgVertx.VX_STOP, getClass().getSimpleName()));
        /** 2.退出Client **/
        this.outlet();
    }

    @Override
    public boolean running() {
        final String address = MessageFormat.format(RmiKeys.VERTX_OPTS, INSTANCE);
        return RemoteRefers.isRunning(address);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void outlet() {
        /** 退出程序 **/
        System.exit(0);
    }
    /** 环境完成 **/
    private void finalizeEnv(final VertxOptions options) throws AbstractException{
        /** 6.将Vertx的启动数据写入RMI **/
        final String address = MessageFormat.format(RmiKeys.VERTX_OPTS, INSTANCE);
        RemoteRefers.registry(address, options.toString());
        /** 7.写入RUNNING写入到关闭位置，如果检索不到这个值就退出 **/
        RemoteRefers.registry(STOP_ADDR, RUNNING);
        /** 8.开启新线程，处理Stop **/
        new Thread(new VertxClosurer(STOP_ADDR, this::outlet)).start();
    }
    
    /** 环境发布 **/
    private void deployEnv(final VertxOptions options) throws AbstractException{
        /** 4.先写入配置到RMI **/
        Promulgator promulgator = null;
        if (options.isClustered()) {
            /** 5.集群发布 **/
            info(LOGGER, MessageFormat.format(MsgVertx.VX_START, getClass().getSimpleName(), INSTANCE, "Cluster"));
            promulgator = singleton(ClusterPromulgator.class, INSTANCE);
        } else {
            info(LOGGER,
                    MessageFormat.format(MsgVertx.VX_START, getClass().getSimpleName(), INSTANCE, "Standalone"));
            /** 5.单节点发布 **/
            promulgator = singleton(SinglePromulgator.class, INSTANCE);
        }
        promulgator.deploy(options);
    }
    
    /** 环境准备 **/
    private void prepareEnv() throws AbstractException{
        /** 2.初始化脚本引擎 **/
        {
            singleton(Injections.Service.SCRIPT_ENGINE);
        }
        /** 3.初始化元数据引擎 **/
        {
            final EnvInstantor instantor = singleton(MilieuBllor.class);
            instantor.buildMilieu();
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