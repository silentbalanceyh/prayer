package com.prayer.handler.web;

import static com.prayer.util.debug.Log.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.log.DebugKey;
import com.prayer.constant.web.WebConfig;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SharedLogoutHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedLogoutHandler.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @return
     */
    public static SharedLogoutHandler create(){
        return new SharedLogoutHandler();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
        context.clearUser();
        context.response().putHeader("location", WebConfig.WEB_ACT_LOGIN_PAGE).setStatusCode(302).end();
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
