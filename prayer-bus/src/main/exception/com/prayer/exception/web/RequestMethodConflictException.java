package com.prayer.exception.web;

import static com.prayer.util.Converter.toStr;

import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public class RequestMethodConflictException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 4859145618005585511L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param op
     * @param method
     */
    public RequestMethodConflictException(final Class<?> clazz, final String op, final HttpMethod[] methods) {
        super(clazz, -30026, op, toStr(methods));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30026;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
