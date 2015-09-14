package com.prayer.handler.security.impl;

import com.google.common.net.HttpHeaders;
import com.prayer.constant.Constants;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.uca.assistant.HttpErrHandler;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicAuthHandlerImpl extends AuthHandlerImpl {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** REALM **/
	@NotNull
	private transient final String realm;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public BasicAuthHandlerImpl(@NotNull final AuthProvider provider, @NotNull @NotBlank @NotEmpty final String realm) {
		super(provider);
		this.realm = realm;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		final User user = routingContext.user();
		if (null == user) {
			final HttpServerRequest request = routingContext.request();
			final String authorization = request.headers().get(HttpHeaders.AUTHORIZATION);
			// 1.找不到Authorization头部信息
			if (null == authorization) {
				this.handler401Error(routingContext);
			}
		} else {
			// Authorized -> 如果已经验证过了则不需要再进行用户信息的验证
			authorise(user, routingContext);
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void handler401Error(final RoutingContext context) {
		final RestfulResult webRet = RestfulResult.create();
		HttpErrHandler.error401(webRet, getClass(), context.request().path());
		context.put(Constants.VX_CTX_ERROR, webRet);
		context.response().putHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
		context.fail(webRet.getStatusCode().status());
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
