package com.prayer.handler.security.impl; // NOPMD

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Dispatcher;
import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.model.web.StatusCode;
import com.prayer.security.provider.AuthConstants.BASIC;
import com.prayer.security.provider.BasicAuth;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.vx.configurator.SecurityConfigurator;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
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
    // ~ Instance Fields =====================================
    /** REALM **/
    @NotNull
    private transient final String realm; // NOPMD
    /** **/
    @NotNull
    private transient final ConfigService service;
    /** **/
    @NotNull
    private transient final SecurityConfigurator configurator;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public BasicAuthHandlerImpl(@NotNull final AuthProvider provider, @NotNull @NotBlank @NotEmpty final String realm) {
        super(provider);
        this.realm = realm;
        this.service = singleton(ConfigSevImpl.class);
        this.configurator = singleton(SecurityConfigurator.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.AUTH);
        /**
         * 1.根据Error设置相应，唯一特殊的情况是Basic认证是Body的参数方式，
         * 但其参数信息是在processAuth的过程填充到系统里的， 一旦出现了com.prayer.exception.web.
         * BodyParamDecodingException异常信息则依然可让其执行认证
         */
        if (this.requestDispatch(context)) {
            final User user = context.user();
            if (null == user) {
                // 5.认证执行代码
                this.processAuth(context);
            } else {
                // 5.不需要认证的情况
                authorise(user, context);
            }
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean requestDispatch(final RoutingContext context) {
        boolean ret = true;
        final String endpoint = this.configurator.getSecurityOptions().getString(BASIC.LOGIN_URL);
        if (StringUtil.equals(context.request().path(), endpoint)) {
            final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service
                    .findUri(context.request().path());
            if (Dispatcher.requestDispatch(getClass(), result, context)) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    private void processAuth(final RoutingContext context) {
        final Requestor requestor = Extractor.requestor(context);
        final JsonObject response = requestor.getResponse();
        // 1.找不到Authorization头部信息
        if (StatusCode.UNAUTHORIZED.status() == response.getInteger(JsonKey.RESPONSE.STATUS)) {
            Future.error401(getClass(), context);
            return;
        } else {
            // 2.直接传入Requestor信息
            this.authProvider.authenticate(requestor.getData(), res -> {
                if (res.succeeded()) {
                    final User authenticated = res.result();
                    context.setUser(authenticated);

                    // 3.客户端和服务端Session同步
                    if (requestor.getToken().containsKey(JsonKey.TOKEN.ID)) {
                        this.processClientLogin(context, requestor.getToken().getString(JsonKey.TOKEN.ID),
                                authenticated);
                    }
                    // 4.通过LOGIN_URL判断二次登录
                    if (requestor.getRequest().containsKey(JsonKey.REQUEST.LOGIN_URL)) {
                        if (StringUtil.equals(requestor.getRequest().getString(JsonKey.REQUEST.LOGIN_URL),
                                context.request().path())) {
                            requestor.getResponse().getJsonObject(JsonKey.RESPONSE.DATA).remove(JsonKey.TOKEN.PASSWORD);
                            authorise(authenticated, context, requestor.getResponse());
                        } else {
                            // Next -> 需要填充Requestor
                            requestor.getResponse().clear();
                            requestor.getParams().clear();
                            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
                            authorise(authenticated, context);
                        }
                    } else {
                        // Next -> 需要填充Requestor
                        requestor.getResponse().clear();
                        requestor.getParams().clear();
                        context.put(Constants.KEY.CTX_REQUESTOR, requestor);
                        authorise(authenticated, context);
                    }
                } else {
                    if (StatusCode.UNAUTHORIZED.status() == requestor.getResponse()
                            .getInteger(JsonKey.RESPONSE.STATUS)) {
                        Future.error401(getClass(), context, requestor.getResponse().getString(JsonKey.RESPONSE.KEY));
                        return;
                    } else {
                        Future.error500(getClass(), context);
                        return;
                    }
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

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
