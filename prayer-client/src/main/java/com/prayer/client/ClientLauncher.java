package com.prayer.client;

import static com.prayer.util.Instance.singleton;

import com.prayer.configurator.VertxConfigurator;

import io.vertx.core.VertxOptions;
import io.vertx.core.spi.VertxFactory;

/**
 * Prayer启动器，整个Prayer Framework的入口程序
 * 
 * @author Lang
 *
 */
public final class ClientLauncher {
	// ~ Static Fields =======================================

	private static final String VX_NAME = "PRAYER-CLIENT";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param args
	 */
	public static void main(final String... args) {
		// 1.构造Vertx整体环境
		final VertxConfigurator configurator = singleton(VertxConfigurator.class);
		final VertxOptions options = configurator.getOptions(VX_NAME);
		final VertxFactory factory = configurator.getFactory();
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private ClientLauncher() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
