package com.prayer.metaserver.h2.util;

import com.prayer.facade.engine.Options;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class UriResolver {
    // ~ Static Fields =======================================
    /** Single **/
    private static final String URI_SINGLE = "jdbc:h2:tcp://{0}:{1}/~/META/{2}";
    /** Cluster **/
    private static final String URI_CLUSTER = "jdbc:h2:tcp://{0}/META/{1}";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static String resolve(@NotNull final Options options) {
        /** 1.构建URI **/
        final StringBuilder uri = new StringBuilder();
        /** 2.判断Cluster **/
        if(isClustered(options)){
            
        }
        /** 3.返回URI **/
        return uri.toString();
    }

    private static boolean isClustered(final Options options) {
        return options.readOpts().containsKey("cluster");
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UriResolver() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
