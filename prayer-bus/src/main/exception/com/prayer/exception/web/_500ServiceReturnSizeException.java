package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class _500ServiceReturnSizeException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 4132299137437760054L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param size
     */
    public _500ServiceReturnSizeException(final Class<?> clazz, final String size){
        super(clazz, -30017, size);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -30017;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}