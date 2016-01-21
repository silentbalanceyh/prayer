package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class PrimaryKeyMissingException extends AbstractWebException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -7665279214253073480L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param keys
     */
    public PrimaryKeyMissingException(final Class<?> clazz, final String keys) {
        super(clazz, -30015, keys);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -30015;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
