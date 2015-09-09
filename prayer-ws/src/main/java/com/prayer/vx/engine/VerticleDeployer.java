package com.prayer.vx.engine;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractVertXException;
import com.prayer.exception.vertx.VerticleInvalidException;
import com.prayer.exception.vertx.VerticleNotFoundException;
import com.prayer.util.Instance;
import com.prayer.vx.configurator.VerticleConfigurator;
import com.prayer.vx.handler.deploy.VerticleAsyncHandler;

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
	public void deployVerticles() throws AbstractVertXException {
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
	private void deploySyncVerticles() throws AbstractVertXException {
		if (!DATA_SYNC.isEmpty() && null != this.vertxRef) {
			info(LOGGER, "[I-VX] (Sync) Verticle count number:  " + DATA_SYNC.size());
			for (final String name : DATA_SYNC.keySet()) {
				// 1.检查当前配置
				this.checkVerticle(name);
				// 2.发布这个Verticle
				this.vertxRef.deployVerticle(name, DATA_SYNC.get(name));
			}
		} else {
			info(LOGGER, "[I-VX] (Sync) Vertx reference = " + this.vertxRef + ", size = " + DATA_SYNC.size());
		}
	}

	/**
	 * 发布所有异步的Verticles
	 * 
	 * @throws AbstractVertXException
	 */
	private void deployAsyncVerticles() throws AbstractVertXException {
		if (!DATA_ASYNC.isEmpty() && null != this.vertxRef) {
			info(LOGGER, "[I-VX] (Async) Verticle count number:  " + DATA_SYNC.size());
			for (final String name : DATA_ASYNC.keySet()) {
				// 1.检查当前配置
				this.checkVerticle(name);
				// 2.发布这个Verticle
				this.vertxRef.deployVerticle(name, DATA_ASYNC.get(name), VerticleAsyncHandler.create());
			}
		} else {
			info(LOGGER, "[I-VX] (Async) Vertx reference = " + this.vertxRef + ", size = " + DATA_ASYNC.size());
		}
	}

	private void checkVerticle(final String className) throws AbstractVertXException {
		// 1.检查是否存在这个类
		Class<?> clazz = Instance.clazz(className);
		if (null == clazz) {
			final AbstractVertXException error = new VerticleNotFoundException(getClass(), className);
			debug(LOGGER, "SYS.VX.CLASS", error, "Verticle", className);
			throw error;
		} else {
			// 2.递归检索父类
			final List<Class<?>> parents = Instance.parents(className);
			boolean flag = false;
			for (final Class<?> item : parents) {
				if (item == AbstractVerticle.class) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				info(LOGGER, "SYS.VX.INVALID", null, "( Extends ) Verticle", className);
				// 3.递归检索接口
				final List<Class<?>> interfaces = Instance.interfaces(className);
				flag = false;
				for (final Class<?> item : interfaces) {
					if (item == Verticle.class) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					final AbstractVertXException error = new VerticleInvalidException(getClass(), className);
					debug(LOGGER, "SYS.VX.INVALID", error, "( Implementation ) Verticle", className);
					throw error;
				}
			}
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
