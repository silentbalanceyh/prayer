package com.prayer.security.handler.impl;

import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.model.web.StatusCode;
import com.prayer.security.handler.BasicAuthHandler;
import com.prayer.security.provider.BasicProvider;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.util.web.Future;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class BasicAuthResultHandler implements Handler<AsyncResult<User>> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final RoutingContext context;
    /** **/
    private transient final Requestor requestor;
    /** **/
    private transient final BasicAuthHandler handler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param context
     * @param requestor
     * @param handler
     * @return
     */
    public static BasicAuthResultHandler create(final BasicAuthHandler handler, final RoutingContext context,
            final Requestor requestor) {
        return new BasicAuthResultHandler(handler, context, requestor);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(final AsyncResult<User> event) {
        if (event.succeeded()) {
            final User authenticated = event.result();
            context.setUser(authenticated);

            // 3.客户端和服务端Session同步
            this.processClientLogin(context, requestor, authenticated);

            // 4.通过LOGIN_URL判断二次登录
            if (requestor.getRequest().containsKey(JsonKey.REQUEST.LOGIN_URL)) {
                /**
                 * 如果context.request().path()是登录入口，那么这个地方认证成功过后就直接Response了
                 * 否则，就继续处理请求，执行权限认证的方法
                 */
                if (StringUtil.equals(requestor.getRequest().getString(JsonKey.REQUEST.LOGIN_URL),
                        context.request().path())) {
                    /**
                     * 如果包含了LOGIN_URL的时候用户名密码对了就不执行权限检查了
                     * 这个地方LOGIN_URL是登录入口BASIC.login.url=/api/sec/login
                     * 而且为了防止context调用next方法，这里需要直接返回响应结果
                     */
                    requestor.getResponse().getJsonObject(JsonKey.RESPONSE.DATA).remove(JsonKey.TOKEN.PASSWORD);
                    /**
                     * 这里的RoutingContext会直接调用end方法结束请求
                     */
                    handler.authorise(authenticated, context, requestor.getResponse());
                } else {
                    /**
                     * 权限认证入口，主要负责调用next方法执行下一个路由
                     */
                    handler.authorise(authenticated, context, requestor);
                }
            } else {
                /**
                 * 权限认证入口，主要负责调用next方法执行下一个路由
                 */
                // 过权限认证入口
                handler.authorise(authenticated, context, requestor);
            }
        } else {
            if (StatusCode.UNAUTHORIZED.code() == requestor.getResponse().getInteger(JsonKey.RESPONSE.STATUS)) {
                Future.error401(getClass(), context, requestor.getResponse().getString(JsonKey.RESPONSE.KEY));
                return;
            } else {
                Future.error500(getClass(), context);
                return;
            }
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 私有构造函数
     * 
     * @param context
     * @param requestor
     */
    private BasicAuthResultHandler(final BasicAuthHandler handler, final RoutingContext context,
            final Requestor requestor) {
        this.context = context;
        this.requestor = requestor;
        this.handler = handler;
    }

    private void processClientLogin(final RoutingContext routingContext, final Requestor requestor, final User user) {
        if (requestor.getToken().containsKey(JsonKey.TOKEN.ID) && user instanceof BasicUser) {
            final String uID = requestor.getToken().getString(JsonKey.TOKEN.ID);
            final BasicUser stored = (BasicUser) user;
            // 根据不同的模式实现
            final SharedData shared = routingContext.vertx().sharedData();
            final LocalMap<String, Buffer> sharedMap = shared.getLocalMap(BasicProvider.KEY_POOL_USER);
            final Buffer buffer = Buffer.buffer();
            stored.writeToBuffer(buffer);
            sharedMap.put(uID, buffer);
        }
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
