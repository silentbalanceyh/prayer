package com.prayer.base.exception;

import static com.prayer.util.debug.Error.error;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractWebException extends AbstractException{
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 8417948701227330418L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractWebException(final String message){
        super(message);
    }
    /**
     * 
     * @param clazz
     * @param errorCode
     * @param params
     */
    public AbstractWebException(final Class<?> clazz, final int errorCode, final Object... params){
        this(error(clazz,errorCode,params));
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
