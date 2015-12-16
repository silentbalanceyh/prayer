package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class DependRuleInvalidException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 2752930385216672214L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param value
     */
    public DependRuleInvalidException(final Class<?> clazz, final String value){
        super(clazz, -30021, value);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30022;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
