package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class InternalServerErrorException extends AbstractWebException{

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 1439272589774091263L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     */
    public InternalServerErrorException(final Class<?> clazz){
        super(clazz, -30000);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -30000;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
