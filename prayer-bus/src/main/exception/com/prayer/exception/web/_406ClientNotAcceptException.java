package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class _406ClientNotAcceptException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -5487938366236241674L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public _406ClientNotAcceptException(final Class<?> clazz, final String mime, final String actual){
        super(clazz, -30030, mime, actual);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30030;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
