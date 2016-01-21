package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class DependQueryInvalidException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 4872740806754646479L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param query
     */
    public DependQueryInvalidException(final Class<?> clazz, final String query){
        super(clazz, -30024, query);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30024;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
