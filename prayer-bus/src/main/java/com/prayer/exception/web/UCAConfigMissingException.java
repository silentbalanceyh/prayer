package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class UCAConfigMissingException extends AbstractWebException {
    // ~ Static Fields =======================================
    
    /**
     * 
     */
    private static final long serialVersionUID = -399033351901524071L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param paramName
     * @param validator
     * @param errorMsg
     */
    public UCAConfigMissingException(final Class<?> clazz, final String paramName, final String validator, final String errorMsg){
        super(clazz, -30008, paramName, validator, errorMsg);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30008;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
