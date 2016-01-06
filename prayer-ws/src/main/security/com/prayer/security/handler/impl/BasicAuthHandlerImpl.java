package com.prayer.security.handler.impl; // NOPMD

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.debug;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.configurator.SecurityConfigurator;
import com.prayer.constant.Constants;
import com.prayer.constant.log.DebugKey;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.model.web.StatusCode;
import com.prayer.security.AuthConstants.BASIC;
import com.prayer.security.handler.BasicAuthHandler;
import com.prayer.util.web.Dispatcher;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Future;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
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
public class BasicAuthHandlerImpl extends AuthHandlerImpl implements BasicAuthHandler {
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
        debug(LOGGER, DebugKey.WEB_STG_HANDLER, getClass().getName(), Constants.ORDER.SEC.AUTH, context.request().path());
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
            ret = Dispatcher.requestDispatch(getClass(), result, context);
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
            this.authProvider.authenticate(requestor.getData(),
                    BasicAuthResultHandler.create(this, context, requestor));
        }
    }

    /**
     * 
     * @param authenticated
     * @param context
     * @param requestor
     */
    @Override
    public void authorise(final User authenticated, final RoutingContext context, final Requestor requestor) {
        // Next -> 需要填充Requestor
        requestor.getResponse().clear();
        requestor.getParams().clear();
        context.put(Constants.KEY.CTX_REQUESTOR, requestor);
        authorise(authenticated, context);
    }

    /**
     * 认证成功过后直接操作的权限控制，和标准内容中的信息一致
     * @param user
     * @param context
     * @param data
     */
    @Override
    public void authorise(final User user, final RoutingContext context, final JsonObject data) {
        final int requiredcount = authorities.size();
        if (requiredcount > 0) {
            final AtomicInteger count = new AtomicInteger();
            final AtomicBoolean sentFailure = new AtomicBoolean();
            final Handler<AsyncResult<Boolean>> authHandler = res -> {
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
            for (final String authority : authorities) {
                user.isAuthorised(authority, authHandler);
            }
        } else {
            // No auth required
            Future.success(context.response(), data.encode());
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
