package com.prayer.vx.verticle;

import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.security.provider.BasicAuth;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.vx.configurator.ServerConfigurator;
import com.prayer.vx.verticle.injector.RouterInjector;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
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
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(WebVerticle.class);
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
		injectShared(router);

		// 6.监听Cluster端口
		server.requestHandler(router::accept).listen();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void injectShared(final Router router) {
		router.route(Constants.VX_DYNAMIC_ADMIN).order(Constants.VX_OD_SHARED).handler(context -> {
			// 1.获取authorization
			final HttpServerRequest request = context.request();
			final String username = request.getParam(BasicAuth.KEY_USER_ID);
			if (null != username) {
				final SharedData data = context.vertx().sharedData();
				final LocalMap<String, Buffer> sharedMap = data.getLocalMap(BasicAuth.KEY_POOL_USER);
				final Buffer buffer = sharedMap.get(username);
				if (null != buffer) {
					final BasicUser user = new BasicUser();
					final int ret = user.readFromBuffer(Constants.ZERO, buffer);
					if (Constants.ZERO < ret) { // NOPMD
						info(LOGGER, user.toString());
						context.setUser(user);
					}
				}
			}
			// 不论是否认证OK都next
			context.next();
		});
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
