package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class ValidationFailureException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 5055446285552923446L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param message
     */
    public ValidationFailureException(final String message){
        super(message);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -30007;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
