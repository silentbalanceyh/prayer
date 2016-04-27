package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class _400ConfigRequiredException extends AbstractWebException {
    // ~ Static Fields =======================================
    
    /**
     * 
     */
    private static final long serialVersionUID = -399033351901524071L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param name
     * @param config
     */
    public _400ConfigRequiredException(final Class<?> clazz, final String name, final JsonObject config){
        super(clazz, -30008, name, config.encode());
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30008;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
