package com.prayer.console.handler;

import com.prayer.constant.Constants;

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
        context.clearUser();
        context.response().putHeader("location", Constants.ACTION.LOGIN_PAGE).setStatusCode(302).end();
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
