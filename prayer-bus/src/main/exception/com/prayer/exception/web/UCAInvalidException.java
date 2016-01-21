package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class UCAInvalidException extends AbstractWebException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 2114841491912840885L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public UCAInvalidException(final Class<?> clazz, final String className, final String specification) {
        super(clazz, -30006, className, specification);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30006;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
