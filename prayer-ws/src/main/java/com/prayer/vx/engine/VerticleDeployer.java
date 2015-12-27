package com.prayer.vx.engine;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.error;
import static com.prayer.util.Log.info;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.handler.deploy.VerticleAsyncHandler;
import com.prayer.vx.configurator.VerticleConfigurator;

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
    /** **/
    private static final String SYNC = "Sync";
    /** **/
    private static final String ASYNC = "Async";
    /** 同步队列 **/
    private static final ConcurrentMap<String, DeploymentOptions> DATA_SYNC = new ConcurrentHashMap<>();
    /** 异步队列 **/
    private static final ConcurrentMap<String, DeploymentOptions> DATA_ASYNC = new ConcurrentHashMap<>();
    /** **/
    private static final String E_VERTICLE_COUNT = "({0}) Vertx reference = {1}, Queue Size = {2}";
    /** **/
    private static final String I_VERTICLE_COUNT = "({0}) Verticle count number = {1}";
    /** **/
    private static final String DP_VERTICLE = "(Sync) Verticle : {0} has been deployed {1} instances successfully";
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
            if (DATA_SYNC.isEmpty()) {
                DATA_SYNC.putAll(configurator.readSyncConfig());
            }
            if (DATA_ASYNC.isEmpty()) {
                DATA_ASYNC.putAll(configurator.readAsyncConfig());
            }
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
        // 1. 先同步发布所有SYNC的Verticles
        this.deploySyncVerticles();
        // 2. 然后异步发布所有ASYNC的Verticles
        this.deployAsyncVerticles();
    }

    // ~ Private Methods =====================================
    /**
     * 发布所有同步的Verticles
     * 
     * @throws AbstractVertXException
     */
    private void deploySyncVerticles() throws AbstractWebException {
        if (DATA_SYNC.isEmpty() || null == this.vertxRef) {
            error(LOGGER, E_VERTICLE_COUNT, SYNC, this.vertxRef, DATA_SYNC.size());
        } else {
            info(LOGGER, I_VERTICLE_COUNT, SYNC, DATA_SYNC.size());
            for (final String name : DATA_SYNC.keySet()) {
                // 1.检查当前配置
                Interruptor.interruptClass(getClass(), name, "Verticle");
                Interruptor.interruptExtends(getClass(), name, AbstractVerticle.class, Verticle.class);
                // 2.发布这个Verticle
                final DeploymentOptions option = DATA_SYNC.get(name);
                this.vertxRef.deployVerticle(name, option);
                info(LOGGER, MessageFormat.format(DP_VERTICLE, name, option.getInstances()));
            }
        }
    }

    /**
     * 发布所有异步的Verticles
     * 
     * @throws AbstractVertXException
     */
    private void deployAsyncVerticles() throws AbstractWebException {
        if (DATA_SYNC.isEmpty() || null == this.vertxRef) {
            error(LOGGER, E_VERTICLE_COUNT, ASYNC, this.vertxRef, DATA_ASYNC.size());
        } else {
            info(LOGGER, I_VERTICLE_COUNT, ASYNC, DATA_ASYNC.size());
            
            for (final String name : DATA_ASYNC.keySet()) {
                // 1.检查当前配置
                Interruptor.interruptClass(getClass(), name, "Verticle");
                Interruptor.interruptExtends(getClass(), name, AbstractVerticle.class, Verticle.class);
                // 2.发布这个Verticle, 这里不要使用singleton
                final DeploymentOptions option = DATA_ASYNC.get(name);
                this.vertxRef.deployVerticle(name, option,
                        instance(VerticleAsyncHandler.class.getName(), name, option));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
