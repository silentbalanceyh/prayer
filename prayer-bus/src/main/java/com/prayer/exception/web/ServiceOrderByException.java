package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class ServiceOrderByException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -2185089278314685749L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     */
    public ServiceOrderByException(final Class<?> clazz){
        super(clazz,-30016);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -30016;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
