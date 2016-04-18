package com.prayer.resource.inceptor;

import com.prayer.fantasm.resource.AbstractInceptor;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class CacheInceptor extends AbstractInceptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用默认的Key构造
     */
    public CacheInceptor() {
        super("file.cache.config");
    }

    /**
     * 私有构造
     * 
     * @param file
     */
    private CacheInceptor(final String file) {
        super(file);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
