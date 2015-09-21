package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class NotAuthorizationException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 3947551012701331247L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param uri
     */
    public NotAuthorizationException(final Class<?> clazz, final String uri) {
        super(clazz, -30014, uri);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30014;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
