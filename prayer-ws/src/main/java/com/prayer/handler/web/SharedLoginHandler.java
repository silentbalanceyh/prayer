package com.prayer.handler.web;

import static com.prayer.util.Log.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.security.provider.BasicProvider;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.log.DebugKey;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 同步远程和客户端之间的Session用的Handler
 * 
 * @author Lang
 *
 */
@Guarded
public final class SharedLoginHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedLoginHandler.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 静态实例化方法
     * 
     * @return
     */
    public static SharedLoginHandler create() {
        return new SharedLoginHandler();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
        // 1.处理Request看是否要填充user
        final HttpServerRequest request = context.request();
        final String userId = request.getParam(BasicProvider.KEY_USER_ID);
        if (StringKit.isNonNil(userId)) {
            initLogin(context, userId);
        }
        // 3.Next
        context.next();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 参数中包含了UID的情况
     * 
     * @param context
     */
    private void initLogin(final RoutingContext context, final String userId) {
        final SharedData data = context.vertx().sharedData();
        final LocalMap<String, Buffer> sharedMap = data.getLocalMap(BasicProvider.KEY_POOL_USER);
        final Buffer buffer = sharedMap.get(userId);
        if (null != buffer) {
            final BasicUser user = new BasicUser();
            final int ret = user.readFromBuffer(Constants.ZERO, buffer);
            if (Constants.ZERO < ret) { // NOPMD
                context.setUser(user);
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
