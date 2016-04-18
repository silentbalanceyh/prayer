package com.prayer.exception.system;

import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class ApiNotSupportException extends AbstractSystemException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -5389839608735033357L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param method
     */
    public ApiNotSupportException(final Class<?> clazz, final String method) {
        super(clazz, -20009, method);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -20009;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
