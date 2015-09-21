package com.prayer.verticle;

import static com.prayer.util.Instance.singleton;

import com.prayer.configurator.ServerConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Web 应用管理平台
 * 
 * @author Lang
 *
 */
@Guarded
public class ExtJSVerticle extends AbstractVerticle {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final ServerConfigurator configurator;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ExtJSVerticle() {
		super();
		this.configurator = singleton(ServerConfigurator.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void start() {
		// 1.根据Options创建Server相关信息
		final HttpServer server = vertx.createHttpServer(this.configurator.getOptions());

		// 2.Web Default
		final Router router = Router.router(vertx);

		// 5.预处理的Handler
		// 6.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
