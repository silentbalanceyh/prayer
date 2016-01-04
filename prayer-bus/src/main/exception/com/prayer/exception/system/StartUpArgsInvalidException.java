package com.prayer.exception.system;

import com.prayer.base.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class StartUpArgsInvalidException extends AbstractSystemException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 673879780028753528L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param arg
     */
    public StartUpArgsInvalidException(final Class<?> clazz, final String arg){
        super(clazz,-20012, arg);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -20012;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
