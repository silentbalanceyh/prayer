package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class UCANotFoundException extends AbstractWebException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 3952954586289967386L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public UCANotFoundException(final Class<?> clazz, final String className) {
        super(clazz, -30005, className);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30005;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
