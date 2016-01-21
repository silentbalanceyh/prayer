package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class ServiceParamMissingException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -8491683210329845390L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param param
     */
    public ServiceParamMissingException(final Class<?> clazz, final String param){
        super(clazz, -30012, param);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30012;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
