package com.prayer.configuration.impl;

import static com.prayer.util.reflection.Instance.instance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.configuration.fetch.ConfigMounder;
import com.prayer.facade.configuration.fetch.ConfigFetcher;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class MoundSelector {
    // ~ Static Fields =======================================
    /** **/
    private static ConcurrentMap<Class<?>, ConfigFetcher> FETCHERS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    static {
        FETCHERS.put(PEVerticle.class, instance(ConfigMounder.class, PEVerticle.class));
        FETCHERS.put(PERoute.class, instance(ConfigMounder.class, PERoute.class));
        FETCHERS.put(PEUri.class, instance(ConfigMounder.class, PEUri.class));
        FETCHERS.put(PERule.class, instance(ConfigMounder.class, PERule.class));
        FETCHERS.put(PEAddress.class, instance(ConfigMounder.class, PEAddress.class));
        FETCHERS.put(PEScript.class, instance(ConfigMounder.class, PEScript.class));
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param cls
     * @return
     */
    public ConfigFetcher fetchers(final Class<?> cls) {
        return FETCHERS.get(cls);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
