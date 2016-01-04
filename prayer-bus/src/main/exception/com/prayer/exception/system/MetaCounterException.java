package com.prayer.exception.system;

import com.prayer.base.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class MetaCounterException extends AbstractSystemException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1441142395594333769L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param field
     * @param type
     * @param typeCount
     */
    public MetaCounterException(final Class<?> clazz, final Integer fieldCount, final String type,
            final Integer typeCount) {
        super(clazz, -20009, fieldCount, type, typeCount);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -20009;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
