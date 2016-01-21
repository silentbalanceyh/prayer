package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class DependParameterInvalidException extends AbstractWebException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 4823534064595430927L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param type
     */
    public DependParameterInvalidException(final Class<?> clazz, final Class<?> type) {
        super(clazz, -30023, type.getName());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30023;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
