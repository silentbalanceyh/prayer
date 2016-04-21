package com.prayer.vertx.config;

import static com.prayer.util.Planar.flat;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.config.EngineOptsIntaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.resource.InceptBus;
import com.prayer.util.warranter.NumericWarranter;

import io.vertx.core.VertxOptions;

/**
 * Vertx配置项
 * 单件模式
 * @author Lang
 *
 */
public class VertxOptsIntaker implements EngineOptsIntaker<String, VertxOptions> {
    // ~ Static Fields =======================================
    /** Vertx实例配置 **/
    private static ConcurrentMap<String, VertxOptions> VERTXS;
    /** 读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Vertx.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, VertxOptions> ingest() throws AbstractException {
        /** 1.验证数字 **/
        final String[] instances = this.buildInstantces();
        for (final String instance : instances) {
            warrantNumeric(instance);
        }
        /** 2.生成选项表 **/
        return this.buildOpts();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, VertxOptions> buildOpts() {
        if (null == VERTXS) {
            VERTXS = new ConcurrentHashMap<>();
            final String[] instances = this.buildInstantces();
            /** 1.迭代 **/
            for (final String instance : instances) {
                /** 3.构建配置 **/
                if (!VERTXS.containsKey(instance)) {
                    /** 添加配置 **/
                    VERTXS.put(instance, this.buildOpt(instance));
                }
            }
        }
        return VERTXS;
    }

    private VertxOptions buildOpt(final String instance) {
        final VertxOptions opt = new VertxOptions();
        // ~ Pool Configuration -----------------------------------
        this.injectPoolOpts(opt, instance);
        // ~ Cluster Configuration --------------------------------
        this.injectClusterOpts(opt, instance);
        // ~ Blocked Configuration --------------------------------
        this.injectBlockedOpts(opt, instance);
        // ~ HA Configuration -------------------------------------
        this.injectHaOpts(opt, instance);
        return opt;
    }

    private void injectPoolOpts(final VertxOptions opt, final String instance) {
        /** Event Loop Size **/
        opt.setEventLoopPoolSize(flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Pool.EVTLOOP_SIZE, instance)),
                VertxOptions.DEFAULT_EVENT_LOOP_POOL_SIZE));
        /** Worker Size **/
        opt.setWorkerPoolSize(flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Pool.WORKER_SIZE, instance)),
                VertxOptions.DEFAULT_WORKER_POOL_SIZE));
        /** Internal Blocking **/
        opt.setInternalBlockingPoolSize(
                flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Pool.INTERNAL_BLOCKING, instance)),
                        VertxOptions.DEFAULT_INTERNAL_BLOCKING_POOL_SIZE));
    }

    private void injectClusterOpts(final VertxOptions opt, final String instance) {
        /** Enable Cluster **/
        opt.setClustered(INCEPTOR.getBoolean(this.buildKey(Point.Vertx.Cluster.ENABLED, instance)));
        /** Host **/
        opt.setClusterHost(flat(INCEPTOR.getString(this.buildKey(Point.Vertx.Cluster.HOST, instance)),
                VertxOptions.DEFAULT_CLUSTER_HOST));
        /** Port **/
        opt.setClusterPort(flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Cluster.PORT, instance)),
                VertxOptions.DEFAULT_CLUSTER_PORT));
        /** Publish Host **/
        opt.setClusterPublicHost(flat(INCEPTOR.getString(this.buildKey(Point.Vertx.Cluster.PUB_HOST, instance)),
                VertxOptions.DEFAULT_CLUSTER_PUBLIC_HOST));
        /** Publish Port **/
        opt.setClusterPublicPort(flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Cluster.PUB_PORT, instance)),
                VertxOptions.DEFAULT_CLUSTER_PUBLIC_PORT));

        /** Ping Interval **/
        opt.setClusterPingInterval(flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Cluster.PING_IV, instance)),
                VertxOptions.DEFAULT_CLUSTER_PING_INTERVAL));
        /** Ping Interval Reply **/
        opt.setClusterPingReplyInterval(
                flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Cluster.PING_IV_REPLY, instance)),
                        VertxOptions.DEFAULT_CLUSTER_PING_REPLY_INTERVAL));
    }

    private void injectBlockedOpts(final VertxOptions opt, final String instance) {
        /** Blocked thread check internal **/
        opt.setBlockedThreadCheckInterval(
                flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Blocked.THREAD_CHK_IV, instance)),
                        VertxOptions.DEFAULT_BLOCKED_THREAD_CHECK_INTERVAL));
        /** Max Event Loop Execute Time **/
        opt.setMaxEventLoopExecuteTime(
                flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Blocked.TIME_MAX_EVTLOOP, instance)),
                        VertxOptions.DEFAULT_MAX_EVENT_LOOP_EXECUTE_TIME));
        /** Max Worker Time **/
        opt.setMaxWorkerExecuteTime(flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Blocked.TIME_MAX_WORKER, instance)),
                VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME));
    }

    private void injectHaOpts(final VertxOptions opt, final String instance) {
        /** HA **/
        opt.setHAEnabled(INCEPTOR.getBoolean(this.buildKey(Point.Vertx.Ha.ENABLED, instance)));
        /** HA Group **/
        opt.setHAGroup(
                flat(INCEPTOR.getString(this.buildKey(Point.Vertx.Ha.GROUP, instance)), VertxOptions.DEFAULT_HA_GROUP));
        /** Quorum Size **/
        opt.setQuorumSize(flat(INCEPTOR.getInt(this.buildKey(Point.Vertx.Ha.QUORUM_SIZE, instance)),
                VertxOptions.DEFAULT_QUORUM_SIZE));
        /** Warning Exception Time **/
        opt.setWarningExceptionTime(
                flat(INCEPTOR.getLong(this.buildKey(Point.Vertx.Ha.WARNING_EXPTIME, instance)), 5l * 1000 * 1000000));
    }

    private void warrantNumeric(final String instance) throws AbstractLauncherException {
        final Warranter vWter = singleton(NumericWarranter.class);
        final String[] params = this.buildKey(instance).toArray(Constants.T_STR_ARR);
        vWter.warrant(INCEPTOR, params);
    }

    private String buildKey(final String pattern, final String instance) {
        return MessageFormat.format(pattern, instance);
    }

    private List<String> buildKey(final String instance) {
        final List<String> keys = new ArrayList<>();
        /** Pool **/
        keys.add(this.buildKey(Point.Vertx.Pool.EVTLOOP_SIZE, instance));
        keys.add(this.buildKey(Point.Vertx.Pool.WORKER_SIZE, instance));
        keys.add(this.buildKey(Point.Vertx.Pool.INTERNAL_BLOCKING, instance));
        /** Cluster **/
        keys.add(this.buildKey(Point.Vertx.Cluster.PING_IV, instance));
        keys.add(this.buildKey(Point.Vertx.Cluster.PING_IV_REPLY, instance));
        keys.add(this.buildKey(Point.Vertx.Cluster.PORT, instance));
        keys.add(this.buildKey(Point.Vertx.Cluster.PUB_PORT, instance));
        /** Blocked **/
        keys.add(this.buildKey(Point.Vertx.Blocked.THREAD_CHK_IV, instance));
        keys.add(this.buildKey(Point.Vertx.Blocked.TIME_MAX_EVTLOOP, instance));
        keys.add(this.buildKey(Point.Vertx.Blocked.TIME_MAX_WORKER, instance));
        /** Ha **/
        keys.add(this.buildKey(Point.Vertx.Ha.QUORUM_SIZE, instance));
        keys.add(this.buildKey(Point.Vertx.Ha.WARNING_EXPTIME, instance));
        return keys;
    }

    private String[] buildInstantces() {
        return INCEPTOR.getArray(Point.Vertx.ACTIVE);
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
