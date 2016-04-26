package com.prayer.vertx.util;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Fault {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param context
     * @param envelop
     */
    public static boolean route(final RoutingContext event, @NotNull final Envelop envelop) {
        final boolean noroute = envelop.succeeded();
        if (!noroute) {
            event.put(WebKeys.Request.ENVP, envelop);
            event.fail(envelop.status().code());
        }
        return noroute;
    }

    /**
     * 
     * @param event
     * @return
     */
    public static Envelop get(final RoutingContext event) {
        return event.get(WebKeys.Request.ENVP);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Fault() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
