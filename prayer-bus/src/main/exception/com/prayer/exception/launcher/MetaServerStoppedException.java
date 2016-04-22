package com.prayer.exception.launcher;

import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
public class MetaServerStoppedException extends AbstractLauncherException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8127168231696251326L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     */
    public MetaServerStoppedException(final Class<?> clazz) {
        super(clazz, -40004);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -40004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
