package com.prayer.console.handler;

import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.WebLogger;
import com.prayer.util.cv.Constants;

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
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.LOGOUT);
        context.clearUser();
        context.response().putHeader("location", Constants.ACTION.LOGIN_PAGE).setStatusCode(302).end();
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
