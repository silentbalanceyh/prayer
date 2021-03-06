package com.prayer.handler.web;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.log.DebugKey;

import io.vertx.core.Handler;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class UserLoggedHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoggedHandler.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static UserLoggedHandler create() {
        return new UserLoggedHandler();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext routingContext) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
        final User user = routingContext.user();
        if (null != user) {
            info(LOGGER, "Logged user : " + user.principal().encode());
            routingContext.put(Constants.WEB.SESSION_USER, user.principal().encode());
        }
        routingContext.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
