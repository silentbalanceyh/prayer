package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class DependantMultiException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -179985579332024790L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param paramName
     */
    public DependantMultiException(final Class<?> clazz, final String paramName) {
        super(clazz, -30020, paramName);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30020;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
