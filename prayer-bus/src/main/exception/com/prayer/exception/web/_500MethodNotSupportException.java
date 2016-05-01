package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.http.HttpMethod;
/**
 * 
 * @author Lang
 *
 */
public class _500MethodNotSupportException extends AbstractWebException {
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
    public _500MethodNotSupportException(final Class<?> clazz, final HttpMethod method) {
        super(clazz, -30006, method.name());
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
