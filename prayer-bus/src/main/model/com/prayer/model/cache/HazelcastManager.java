package com.prayer.model.cache;

import com.prayer.facade.cache.Cache;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastManager implements Cache {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> void put(String key, T cached) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T get(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
