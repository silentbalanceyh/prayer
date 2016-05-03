package com.prayer.vertx.web.router;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;
import com.prayer.secure.handler.AuthorizeKeaper;

import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SecureFabricator extends AbstractFabricator implements Fabricator {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void immitRouter(@NotNull final Router router) {
        /** Secure **/
        router.route(PATH_SEC_API).order(orders().getInt(Point.Web.Orders.Api.AUTH))
                .blockingHandler(this.buildSecure());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @return
     */
    private AuthorizeKeaper buildSecure() {
        final AuthProvider provider = singleton(this.securitor().getClass(Point.Security.PROVIDER));
        final AuthorizeKeaper keaper = singleton(AuthorizeKeaper.class, provider);
        // TODO：扩展Security
        return keaper;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
