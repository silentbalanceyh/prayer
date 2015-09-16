package com.prayer.handler.security.impl;

import static com.prayer.uca.assistant.WebLogger.error;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.security.provider.BasicAuth;
import com.prayer.uca.assistant.HttpErrHandler;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.Encryptor;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
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
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthHandlerImpl.class);
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
			} else {
				// 2.获取AuthInfo的信息
				final JsonObject authInfo = this.generateAuthInfo(routingContext, authorization);
				// 3.设置扩展信息
				{
					final JsonObject extension = new JsonObject();
					extension.put(Constants.PARAM_ID, routingContext.session().id());
					extension.put(Constants.PARAM_METHOD, request.method().toString());	// 暂时定义传Method
					authInfo.put(BasicAuth.DFT_EXTENSION, extension);
				}
				// 4.认证授权信息
				this.authProvider.authenticate(authInfo, res -> {
					if (res.succeeded()) {
						final User authenticated = res.result();
						// 认证成功的时候放入信息到Body中
						routingContext.setBody(Buffer.buffer(authInfo.encode(), Resources.SYS_ENCODING.name()));
						routingContext.setUser(authenticated);
						authorise(authenticated, routingContext);
					} else {
						this.handler401Error(routingContext);
					}
				});
			}
		} else {
			// Authorized -> 如果已经验证过了则不需要再进行用户信息的验证
			authorise(user, routingContext);
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private JsonObject generateAuthInfo(final RoutingContext routingContext, final String authorization) {
		String username;
		String password;
		String schema;
		final JsonObject retAuthInfo = new JsonObject();
		try {
			final String[] parts = authorization.split(String.valueOf(Symbol.SPACE));
			schema = parts[0];
			final String[] credentials = new String(Base64.getDecoder().decode(parts[1]))
					.split(String.valueOf(Symbol.COLON));
			username = credentials[0];
			password = credentials.length > 1 ? credentials[1] : null; // NOPMD
			if ("Basic".equals(schema)) {
				retAuthInfo.put("username", username);
				retAuthInfo.put("password", Encryptor.encryptMD5(password));
			} else {
				handler401Error(routingContext);
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			error(LOGGER, WebLogger.E_COMMON_EXP, ex.toString());
			handler401Error(routingContext);
		}
		return retAuthInfo;
	}

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
