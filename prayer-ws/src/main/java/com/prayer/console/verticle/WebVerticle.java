package com.prayer.console.verticle;

import static com.prayer.util.Instance.singleton;

import com.prayer.assistant.RouterInjector;
import com.prayer.console.handler.SharedLoginHandler;
import com.prayer.constant.Constants;
import com.prayer.vx.configurator.ServerConfigurator;

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
public class WebVerticle extends AbstractVerticle {
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
	public WebVerticle() {
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
		final HttpServer server = vertx.createHttpServer(this.configurator.getWebOptions());		

		// 2.Web Default
		final Router router = Router.router(vertx);
		RouterInjector.injectWebDefault(router);

		// 3.Static静态资源
		RouterInjector.injectStatic(router, this.configurator.getEndPoint());

		// 4.Session的使用设置
		RouterInjector.injectSession(vertx, router);

		// 5.预处理的Handler
		router.route(Constants.WEB.DYNAMIC_ADMIN).order(Constants.ORDER.SHARED).handler(SharedLoginHandler.create());

		// 6.添加Logout的Handler
		injectLogout(router);

		// 7.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void injectLogout(final Router router) {
		// 登录按钮
		router.route(Constants.ACTION.LOGOUT).order(Constants.ORDER.LOGOUT).handler(context -> {
			context.clearUser();
			context.response().putHeader("location", Constants.ACTION.LOGIN_PAGE).setStatusCode(302).end();
		});
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}