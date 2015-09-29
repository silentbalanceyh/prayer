package com.prayer.handler.security.impl; // NOPMD

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.Base64;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Dispatcher;
import com.prayer.assistant.Future;
import com.prayer.assistant.HttpErrHandler;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.BodyParamDecodingException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.security.provider.AuthConstants;
import com.prayer.security.provider.AuthConstants.BASIC;
import com.prayer.security.provider.BasicAuth;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.util.Encryptor;
import com.prayer.util.StringKit;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import jodd.util.StringUtil;
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
    /** **/
    private static final String AUTH_KEY = "response";
    // ~ Instance Fields =====================================
    /** REALM **/
    @NotNull
    private transient final String realm; // NOPMD
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
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.AUTH);
        // 1.获取请求Request和相应Response引用
        final HttpServerRequest request = routingContext.request();
        // 2.从系统中按URI读取接口规范
        final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service.findUri(request.path());
        final RestfulResult webRet = RestfulResult.create();

        // 3.请求转发，去除掉Error过后的信息
        final AbstractException error = Dispatcher.requestDispatch(result, webRet, routingContext, getClass());

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
            routingContext.put(Constants.KEY.CTX_ERROR, webRet);
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
            return; // NOPMD
        } else {
            // 2.获取AuthInfo的信息
            final JsonObject authInfo = this.generateAuthInfo(routingContext, authorization);
            // Fix Issue -> Response has already been sent.
            if (401 == authInfo.getInteger(AUTH_KEY)) { // NOPMD
                return;
            } else {
                authInfo.remove(AUTH_KEY);
            }
            // 3.设置扩展信息
            {
                final JsonObject extension = new JsonObject();
                // 防止Session ID空指针异常
                if (null != routingContext.session()) {
                    extension.put(Constants.PARAM.ID, routingContext.session().id());
                }
                extension.put(Constants.PARAM.METHOD, request.method().toString()); // 暂时定义传Method
                // 返回值的设置
                authInfo.put(AuthConstants.BASIC.EXTENSION, extension);
            }
            // 4.认证授权信息
            this.authProvider.authenticate(authInfo, res -> {
                if (res.succeeded()) {
                    final User authenticated = res.result();
                    // 认证成功的时候放入信息到User中
                    routingContext.setUser(authenticated);
                    // 放入到LocalData中用于在WebVerticle特殊环境中设置登录信息，主要用于Cross
                    final JsonObject retExt = authInfo.containsKey(BASIC.EXTENSION)
                            ? authInfo.getJsonObject(BASIC.EXTENSION) : null;
                    if (null != retExt) {
                        // Verticle的操作
                        if (retExt.containsKey(BasicAuth.KEY_USER_ID)) {
                            processClientLogin(routingContext, retExt.getString(BasicAuth.KEY_USER_ID), authenticated);
                        }
                        // 将数据放入Session中
                        // 防止二次认证
                        info(LOGGER, " Request : " + request.path() + ", Method : " + request.method().toString());
                        if (retExt.containsKey(BASIC.LOGIN_URL)) {
                            if (StringUtil.equals(request.path(), retExt.getString(BASIC.LOGIN_URL))) {
                                final JsonObject ret = new JsonObject();
                                ret.put(Constants.RET.STATUS_CODE, StatusCode.OK.status());
                                ret.put(Constants.RET.RESPONSE, ResponseCode.SUCCESS);
                                // 返回值中过滤掉密码
                                final JsonObject data = retExt.getJsonObject(Constants.PARAM.DATA);
                                data.remove("password");
                                ret.put(Constants.RET.DATA, data);

                                this.authorise(authenticated, routingContext, ret);
                            } else {
                                this.authorise(authenticated, routingContext);
                            }
                        } else {
                            // 防止本身URL的认证
                            authorise(authenticated, routingContext);
                        }
                    }
                } else {
                    if (StringKit.isNonNil(authInfo.getString(Constants.RET.AUTH_ERROR))) {
                        // 带有返回值的401信息
                        routingContext.put(Constants.RET.AUTH_ERROR, authInfo.getString(Constants.RET.AUTH_ERROR));
                    }
                    this.handler401Error(routingContext);
                }
            });
        }
    }

    protected void authorise(final User user, final RoutingContext context, final JsonObject data) {
        int requiredcount = authorities.size();
        if (requiredcount > 0) {
            AtomicInteger count = new AtomicInteger();
            AtomicBoolean sentFailure = new AtomicBoolean();

            Handler<AsyncResult<Boolean>> authHandler = res -> {
                if (res.succeeded()) {
                    if (res.result()) {
                        if (count.incrementAndGet() == requiredcount) {
                            Future.success(context.response(), data.encode());
                        }
                    } else {
                        if (sentFailure.compareAndSet(false, true)) {
                            context.fail(403);
                        }
                    }
                } else {
                    context.fail(res.cause());
                }
            };
            for (String authority : authorities) {
                user.isAuthorised(authority, authHandler);
            }
        } else {
            // No auth required
            Future.success(context.response(), data.encode());
        }
    }

    private void processClientLogin(final RoutingContext routingContext, final String uID, final User user) {
        if (user instanceof BasicUser) {
            final BasicUser stored = (BasicUser) user;
            final SharedData shared = routingContext.vertx().sharedData();
            final LocalMap<String, Buffer> sharedMap = shared.getLocalMap(BasicAuth.KEY_POOL_USER);
            final Buffer buffer = Buffer.buffer();
            stored.writeToBuffer(buffer);
            sharedMap.put(uID, buffer);
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
                retAuthInfo.put(AUTH_KEY, 200);
                // 放入Token
                retAuthInfo.put(BasicAuth.KEY_TOKEN, authorization);
            } else {
                retAuthInfo.put(AUTH_KEY, 401);
                handler401Error(routingContext);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            error(LOGGER, WebLogger.E_COMMON_EXP, ex.toString());
            retAuthInfo.put(AUTH_KEY, 401);
            handler401Error(routingContext);
        }
        return retAuthInfo;
    }

    private void handler401Error(final RoutingContext context) {
        final RestfulResult webRet = RestfulResult.create();
        HttpErrHandler.error401(webRet, getClass(), context.request().path());
        context.put(Constants.KEY.CTX_ERROR, webRet);
        // Digest认证才会使用的头部信息
        // context.response().putHeader("WWW-Authenticate", "Basic realm=\"" +
        // this.realm + "\"");
        context.fail(webRet.getStatusCode().status());
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
