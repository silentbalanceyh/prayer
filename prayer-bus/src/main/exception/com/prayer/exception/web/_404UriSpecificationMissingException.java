package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class _404UriSpecificationMissingException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -6022461685311274101L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param path
     */
    public _404UriSpecificationMissingException(final Class<?> clazz, final String path){
        super(clazz, -30002, path);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -30002;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
