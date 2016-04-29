package com.prayer.vertx.web.model;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.exception.web._400ResolverMissingException;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.request.Resolver;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Refiner {
    // ~ Static Fields =======================================
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Resolver.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param mime
     * @return
     */
    public static Resolver resolve(@NotNull @NotEmpty @NotBlank final String mime) throws AbstractWebException {
        final Class<?> resolverCls = INCEPTOR.getClass(mime);
        if (null == resolverCls) {
            throw new _400ResolverMissingException(Refiner.class, INCEPTOR.getString(mime), mime);
        }
        return singleton(resolverCls);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Refiner() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
