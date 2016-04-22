package com.prayer.vertx.web.route;

import java.util.List;

import com.prayer.facade.vtx.route.HubFabricator;
import com.prayer.model.meta.vertx.PERoute;

import io.vertx.ext.web.Route;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MimeFabricator implements HubFabricator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void immitRoute(@NotNull final Route routeRef, @NotNull final PERoute entity) {
        /** 1.Consume Mime **/
        List<String> mimes = entity.getConsumerMimes();
        if (null != mimes && !mimes.isEmpty()) {
            for (final String mime : mimes) {
                routeRef.consumes(mime);
            }
        }
        /** 2.暂时不设Produce Mime **/
        // TODO: 需要添加Produce Mime
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
