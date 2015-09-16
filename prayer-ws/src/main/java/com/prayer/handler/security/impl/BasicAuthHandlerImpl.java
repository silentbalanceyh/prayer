package com.prayer.handler.security.impl; // NOPMD

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.Base64;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.BodyParamDecodingException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.security.provider.BasicAuth;
import com.prayer.uca.assistant.HttpErrHandler;
import com.prayer.uca.assistant.SharedDispatcher;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.Encryptor;
import com.prayer.util.StringKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
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
	/** **/
	@NotNull
	private transient final ConfigService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public BasicAuthHandlerImpl(@NotNull final AuthProvider provider, @NotNull @NotBlank @NotEmpty final String realm) {
		super(provider);
		this.realm = realm;
		this.service = singleton(ConfigSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.VX_OD_AUTH);
		// 1.获取请求Request和相应Response引用
		final HttpServerRequest request = routingContext.request();

		// 2.从系统中按URI读取接口规范
		final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service.findUri(request.path());
		final RestfulResult webRet = RestfulResult.create();

		// 3.请求转发，去除掉Error过后的信息
		final AbstractException error = SharedDispatcher.requestDispatch(result, webRet, routingContext, getClass());

		/**
		 * 4.根据Error设置相应，唯一特殊的情况是Basic认证是Body的参数方式，
		 * 但其参数信息是在processAuth的过程填充到系统里的， 一旦出现了com.prayer.exception.web.
		 * BodyParamDecodingException异常信息则依然可让其执行认证
		 */
		if (null == error || error instanceof BodyParamDecodingException) {
			final User user = routingContext.user();
			if (null == user) {
				// 5.认证执行代码
				this.processAuth(routingContext);
			} else {
				// 5.不需要认证的情况
				authorise(user, routingContext);
			}
		} else {
			// 5.直接Dennied的情况
			routingContext.put(Constants.VX_CTX_ERROR, webRet);
			routingContext.fail(webRet.getStatusCode().status());
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void processAuth(final RoutingContext routingContext) {
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
				// 防止Session ID空指针异常
				if (null != routingContext.session()) {
					extension.put(Constants.PARAM_ID, routingContext.session().id());
				}
				extension.put(Constants.PARAM_METHOD, request.method().toString()); // 暂时定义传Method
				// 返回值的设置
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
					if (StringKit.isNonNil(authInfo.getString(BasicAuth.RET_E_KEY))) {
						// 带有返回值的401信息
						routingContext.put(BasicAuth.RET_E_KEY, authInfo.getString(BasicAuth.RET_E_KEY));
					}
					this.handler401Error(routingContext);
				}
			});
		}
	}

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
