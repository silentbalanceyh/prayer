package com.prayer.util.vertx;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Feature {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 没有进入Failure Handler的时候直接
     * 
     * @param event
     */
    public static void next(final RoutingContext event) {
        if (!event.failed()) {
            event.next();
        }
    }

    /**
     * 直接进入Failure的结果
     * 
     * @param event
     * @param stumer
     */
    public static boolean route(final RoutingContext event, @NotNull final Envelop stumer) {
        final boolean success = stumer.succeeded();
        if (!success) {
            event.put(WebKeys.Request.ERR_ENVP, stumer);
            event.fail(stumer.status().code());
        }
        return success;
    }

    /**
     * 
     * @param event
     * @return
     */
    public static Envelop get(final RoutingContext event) {
        return event.get(WebKeys.Request.ERR_ENVP);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Feature() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
