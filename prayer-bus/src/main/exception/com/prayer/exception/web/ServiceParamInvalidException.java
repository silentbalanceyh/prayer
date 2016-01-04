package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class ServiceParamInvalidException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -6393877199785119675L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param param
     */
    public ServiceParamInvalidException(final Class<?> clazz, final String message){
        super(clazz, -30013, message);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30013;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
