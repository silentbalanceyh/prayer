package com.prayer.secure.handler;

import static com.prayer.util.reflection.Instance.instance;

import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.secure.SecureKeaper;
import com.prayer.facade.secure.Token;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.web.StatusCode;
import com.prayer.resource.InceptBus;
import com.prayer.util.vertx.Feature;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class AuthorizeKeaper extends AuthHandlerImpl implements SecureKeaper {
    // ~ Static Fields =======================================
    /** Security Inceptor **/
    private static final Inceptor SECIPTOR = InceptBus.build(Point.Security.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AuthorizeKeaper(@NotNull final AuthProvider provider) {
        super(provider);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext event) {
        /** 1.提取Token **/
        final Token token = instance(SECIPTOR.getClass(Point.Security.TOKEN), event.request());
        /** 2.判断Token是否取到 **/
        if (token.obtained()) {
            /** 3.验证 **/
            this.authProvider.authenticate(this.buildCredential(event, token), result -> {
                if (result.succeeded()) {
                    /** 4.认证成功 **/
                    final User user = result.result();
                    System.out.println(user);
                    Feature.next(event);
                } else {
                    /** 4.认证失败 **/
                    final Throwable error = result.cause();
                    /** 捕捉不了401的错误就直接500返回 **/
                    Envelop stumer = Envelop.failure(new _500InternalServerErrorException(getClass()));
                    /** 4.1.认证失败的信息 **/
                    if (error instanceof AbstractException) {
                        stumer = Envelop.failure((AbstractException) error, StatusCode.UNAUTHORIZED);
                    }
                    /** ERROR-ROUTE：错误路由 **/
                    Feature.route(event, stumer);
                }
            });
        } else {
            /** 3.从Header中没有拿到Token **/
            final Envelop stumer = Envelop.failure(token.getError(), StatusCode.UNAUTHORIZED);
            /** ERROR-ROUTE：错误路由 **/
            Feature.route(event, stumer);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildCredential(final RoutingContext event, final Token token) {
        final JsonObject credential = new JsonObject();
        /** 1.提取URI **/
        final PEUri uri = event.get(WebKeys.Request.Data.Meta.PEURI);
        credential.put(SecKeys.RES_ID, uri.getUniqueId());
        /** 2.访问的URI **/
        credential.put(SecKeys.URI, event.request().path());
        /** 3.用户访问的令牌信息 **/
        credential.put(SecKeys.CREDENTIAL, token.getCredential());
        return credential;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
