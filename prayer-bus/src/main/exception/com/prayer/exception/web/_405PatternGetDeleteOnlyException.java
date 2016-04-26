package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class _405PatternGetDeleteOnlyException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -5943831802454802762L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param method
     */
    public _405PatternGetDeleteOnlyException(final Class<?> clazz, final String method){
        super(clazz, -30027, method);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30027;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
