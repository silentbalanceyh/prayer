package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class DependParamsMissingException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -594147386772603070L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param field
     */
    public DependParamsMissingException(final Class<?> clazz, final String param, final String field) {
        super(clazz, -30021, param, field);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30021;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
