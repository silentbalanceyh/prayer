package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class UCAConfigErrorException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 5463958285630375991L;
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
    public UCAConfigErrorException(final Class<?> clazz, final String paramName, final String validator, final String errorMsg){
        super(clazz, -30004, paramName, validator, errorMsg);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -30004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
