package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class BodyParamDecodingException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8108120269473430350L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param uri
     */
    public BodyParamDecodingException(final Class<?> clazz, final String uri) {
        super(clazz, -30010, uri);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30010;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
