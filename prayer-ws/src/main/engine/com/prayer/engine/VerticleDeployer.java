package com.prayer.engine;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.configurator.VerticleConfigurator;
import com.prayer.handler.deploy.VerticleAsyncHandler;
import com.prayer.util.web.Interruptor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VerticleDeployer {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleDeployer.class);
    /** 发布项队列 **/
    private static final ConcurrentMap<String, DeploymentOptions> DEPLOY_OPTS = new ConcurrentHashMap<>();
    /** **/
    private static final String E_VERTICLE_COUNT = "Vertx reference = {0}, Queue Size = {1}";
    /** **/
    private static final String I_VERTICLE_COUNT = "Verticle count number = {0}";
    // ~ Instance Fields =====================================

    /** Vertx的唯一全局引用 **/
    @NotNull
    private transient final Vertx vertxRef;

    /** **/
    private transient VerticleConfigurator configurator;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 构造函数 **/
    @PostValidateThis
    public VerticleDeployer(@NotNull final Vertx vertxRef) {
        if (null == configurator) {
            configurator = singleton(VerticleConfigurator.class);
            DEPLOY_OPTS.putAll(configurator.readConfig());
        }
        this.vertxRef = vertxRef;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 发布所有的Verticles
     * 
     * @throws AbstractVertXException
     */
    public void deployVerticles() throws AbstractWebException {
        if (DEPLOY_OPTS.isEmpty() || null == this.vertxRef) {
            error(LOGGER, E_VERTICLE_COUNT, this.vertxRef, DEPLOY_OPTS.size());
        } else {
            info(LOGGER, I_VERTICLE_COUNT, DEPLOY_OPTS.size());

            for (final String name : DEPLOY_OPTS.keySet()) {
                // 1.检查当前配置
                Interruptor.interruptClass(getClass(), name, "Verticle");
                Interruptor.interruptExtends(getClass(), name, AbstractVerticle.class, Verticle.class);
                // 2.发布这个Verticle, 这里不要使用singleton
                final DeploymentOptions option = DEPLOY_OPTS.get(name);
                this.vertxRef.deployVerticle(name, option,
                        instance(VerticleAsyncHandler.class.getName(), name, option));
            }
        }
    }

    // ~ Private Methods =====================================
    /*    *//**
             * 发布所有同步的Verticles
             * 
             * @throws AbstractVertXException
             *//*
               * private void deploySyncVerticles() throws AbstractWebException
               * { if (DATA_SYNC.isEmpty() || null == this.vertxRef) {
               * error(LOGGER, E_VERTICLE_COUNT, SYNC, this.vertxRef,
               * DATA_SYNC.size()); } else { info(LOGGER, I_VERTICLE_COUNT,
               * SYNC, DATA_SYNC.size()); for (final String name :
               * DATA_SYNC.keySet()) { // 1.检查当前配置
               * Interruptor.interruptClass(getClass(), name, "Verticle");
               * Interruptor.interruptExtends(getClass(), name,
               * AbstractVerticle.class, Verticle.class); // 2.发布这个Verticle
               * final DeploymentOptions option = DATA_SYNC.get(name);
               * this.vertxRef.deployVerticle(name, option); info(LOGGER,
               * MessageFormat.format(DP_VERTICLE, name,
               * option.getInstances())); } } }
               */
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
