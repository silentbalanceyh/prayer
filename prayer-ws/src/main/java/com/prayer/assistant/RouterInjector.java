package com.prayer.assistant;

import static com.prayer.util.Instance.singleton;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.SecurityMode;
import com.prayer.handler.security.BasicAuthHandler;
import com.prayer.vx.configurator.SecurityConfigurator;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.RedirectAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.JadeTemplateEngine;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RouterInjector {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param vertx
	 * @param router
	 */
	public static void injectSession(@NotNull final Vertx vertx, @NotNull final Router router) {
		SessionHandler handler = SessionHandler.create(ClusteredSessionStore.create(vertx));
		if (vertx.isClustered()) {
			handler = SessionHandler.create(ClusteredSessionStore.create(vertx));
		} else {
			handler = SessionHandler.create(LocalSessionStore.create(vertx));
		}
		router.route().order(Constants.ORDER.SESSION).handler(handler);
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		final AuthProvider authProvider = securitor.getProvider();
		router.route().order(Constants.ORDER.USER_SESSION).handler(UserSessionHandler.create(authProvider));
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectWebDefault(@NotNull final Router router) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		router.route().order(Constants.ORDER.COOKIE).handler(CookieHandler.create());
		router.route().order(Constants.ORDER.BODY).handler(BodyHandler.create());
		router.route().order(Constants.ORDER.CORS).handler(securitor.getCorsHandler());
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectSecurity(@NotNull final Router router) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		final AuthProvider authProvider = securitor.getProvider();
		if (SecurityMode.BASIC == securitor.getMode()) {
			router.route(Constants.ROUTE.SECURE_API).order(Constants.ORDER.AUTH)
					.handler(BasicAuthHandler.create(authProvider));
		}
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectStatic(@NotNull final Router router, @NotNull @NotBlank @NotEmpty final String apiUrl) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		router.route(Constants.WEB.STATIC_ROUTE).order(Constants.ORDER.STATIC)
				.handler(StaticHandler.create().setCachingEnabled(false));
		// 引入jade模板引擎
		final TemplateHandler handler = TemplateHandler.create(JadeTemplateEngine.create());
		router.route(Constants.WEB.DYNAMIC_ROUTE).order(Constants.ORDER.DYNAMIC).handler(context -> {
			context.put(Constants.KEY.API_URL, apiUrl);
			context.next();
		});
		router.route(Constants.WEB.DYNAMIC_ROUTE).order(Constants.ORDER.CONTEXT).handler(handler);
		router.route(Constants.WEB.STATIC_ROUTE).order(Constants.ORDER.STATIC).failureHandler(ErrorHandler.create());
		router.route(Constants.WEB.FAVICON_ICO).order(Constants.ORDER.DYNAMIC)
				.handler(FaviconHandler.create(Constants.WEB.FAVICON_PATH));
		// Redirect问题
		final AuthProvider authProvider = securitor.getProvider();
		final AuthHandler redirectHandler = RedirectAuthHandler.create(authProvider, Constants.ACTION.LOGIN_PAGE);
		router.route(Constants.WEB.DYNAMIC_ADMIN).order(Constants.ORDER.ADMIN).handler(redirectHandler);
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private RouterInjector() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
