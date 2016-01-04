package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class ConvertorMultiException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -3323759306756047470L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param paramName
     */
    public ConvertorMultiException(final Class<?> clazz, final String paramName){
        super(clazz, -30009, paramName);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30009;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
