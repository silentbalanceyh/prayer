package com.prayer.security.handler;

import com.prayer.model.Requestor;
import com.prayer.security.handler.impl.BasicAuthHandlerImpl;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;

/**
 * 自定义的BasicAuthHandler，替换Vertx中的BasicAuthHandler用
 * 
 * @author Lang
 *
 */
@VertxGen
public interface BasicAuthHandler extends AuthHandler {
    /** DEFAULT REALM **/
    String DEFAULT_REALM = "PRAYER-BUS";

    /**
     * 默认的Basic的Handler创建
     * 
     * @param authProvider
     * @return
     */
    static AuthHandler create(final AuthProvider authProvider) {
        return new BasicAuthHandlerImpl(authProvider, DEFAULT_REALM);
    }

    /**
     * 
     * @param authProvider
     * @param realm
     * @return
     */
    static AuthHandler create(final AuthProvider authProvider, final String realm) {
        return new BasicAuthHandlerImpl(authProvider, realm);
    }
    /**
     * 用户认证，基本类的认证信息
     * @param user
     * @param context
     * @param response
     */
    void authorise(User user, RoutingContext context, JsonObject response);
    /**
     * 权限控制，认证之后处理
     * @param user
     * @param context
     * @param requestor
     */
    void authorise(User user, RoutingContext context, Requestor requestor);
}
