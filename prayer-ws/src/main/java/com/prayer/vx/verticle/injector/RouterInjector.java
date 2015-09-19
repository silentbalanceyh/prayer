package com.prayer.vx.verticle.injector;

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
		router.route().order(Constants.VX_OD_SESSION).handler(handler);
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		final AuthProvider authProvider = securitor.getProvider();
		router.route().order(Constants.VX_OD_USER_SESSION).handler(UserSessionHandler.create(authProvider));
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectWebDefault(@NotNull final Router router) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		router.route().order(Constants.VX_OD_COOKIE).handler(CookieHandler.create());
		router.route().order(Constants.VX_OD_BODY).handler(BodyHandler.create());
		router.route().order(Constants.VX_OD_CORS).handler(securitor.getCorsHandler());
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectSecurity(@NotNull final Router router) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		final AuthProvider authProvider = securitor.getProvider();
		if (SecurityMode.BASIC == securitor.getMode()) {
			router.route(Constants.VX_SECURE_API_ROOT).order(Constants.VX_OD_AUTH)
					.handler(BasicAuthHandler.create(authProvider));
		}
	}

	/**
	 * 
	 * @param router
	 */
	public static void injectStatic(@NotNull final Router router, @NotNull @NotBlank @NotEmpty final String apiUrl) {
		final SecurityConfigurator securitor = singleton(SecurityConfigurator.class);
		router.route(Constants.VX_STATIC_ROOT).order(Constants.VX_OD_STATIC)
				.handler(StaticHandler.create().setCachingEnabled(false));
		// 引入jade模板引擎
		final TemplateHandler handler = TemplateHandler.create(JadeTemplateEngine.create());
		router.route(Constants.VX_DYNAMIC_ROOT).order(Constants.VX_OD_DYNAMIC).handler(context -> {
			context.put(Constants.VX_API_DK, apiUrl);
			context.next();
		});
		router.route(Constants.VX_DYNAMIC_ROOT).order(Constants.VX_OD_CONTEXT).handler(handler);
		router.route(Constants.VX_STATIC_ROOT).order(Constants.VX_OD_STATIC).failureHandler(ErrorHandler.create());
		router.route(Constants.VX_FAVICON_ROOT).order(Constants.VX_OD_DYNAMIC)
				.handler(FaviconHandler.create(Constants.VX_FAVICON_PATH));
		// Redirect问题
		final AuthProvider authProvider = securitor.getProvider();
		final AuthHandler redirectHandler = RedirectAuthHandler.create(authProvider, Constants.VX_LOGIN_PAGE);
		router.route(Constants.VX_DYNAMIC_ADMIN).order(Constants.VX_OD_ADMIN).handler(redirectHandler);
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
