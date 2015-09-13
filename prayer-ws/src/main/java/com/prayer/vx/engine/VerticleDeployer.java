package com.prayer.vx.engine;

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractWebException;
import com.prayer.handler.deploy.VerticleAsyncHandler;
import com.prayer.uca.assistant.Interruptor;
import com.prayer.uca.assistant.WebLogger;
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
	/** **/
	private static VerticleConfigurator configurator;
	/** 同步队列 **/
	private static ConcurrentMap<String, DeploymentOptions> DATA_SYNC = new ConcurrentHashMap<>();
	/** 异步队列 **/
	private static ConcurrentMap<String, DeploymentOptions> DATA_ASYNC = new ConcurrentHashMap<>();

	// ~ Instance Fields =====================================

	/** Vertx的唯一全局引用 **/
	@NotNull
	private transient Vertx vertxRef;

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
		if (!DATA_SYNC.isEmpty() && null != this.vertxRef) {
			info(LOGGER, WebLogger.I_VERTICLE_COUNT, SYNC, DATA_SYNC.size());
			for (final String name : DATA_SYNC.keySet()) {
				// 1.检查当前配置
				Interruptor.interruptClass(getClass(), name, "Verticle");
				Interruptor.interruptExtends(getClass(), name, AbstractVerticle.class, Verticle.class);
				// 2.发布这个Verticle
				this.vertxRef.deployVerticle(name, DATA_SYNC.get(name));
			}
		} else {
			error(LOGGER, WebLogger.E_VERTICLE_COUNT, SYNC, this.vertxRef, DATA_SYNC.size());
		}
	}

	/**
	 * 发布所有异步的Verticles
	 * 
	 * @throws AbstractVertXException
	 */
	private void deployAsyncVerticles() throws AbstractWebException {
		if (!DATA_ASYNC.isEmpty() && null != this.vertxRef) {
			info(LOGGER, WebLogger.I_VERTICLE_COUNT, ASYNC, DATA_ASYNC.size());
			for (final String name : DATA_ASYNC.keySet()) {
				// 1.检查当前配置
				Interruptor.interruptClass(getClass(), name, "Verticle");
				Interruptor.interruptExtends(getClass(), name, AbstractVerticle.class, Verticle.class);
				// 2.发布这个Verticle
				this.vertxRef.deployVerticle(name, DATA_ASYNC.get(name), VerticleAsyncHandler.create());
			}
		} else {
			error(LOGGER, WebLogger.E_VERTICLE_COUNT, ASYNC, this.vertxRef, DATA_ASYNC.size());
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
