package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;
/**
 * 
 * @author Lang
 *
 */
public class JSScriptEngineException extends AbstractWebException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -163631521238575428L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param scriptMsg
     */
    public JSScriptEngineException(final Class<?> clazz, final String scriptMsg){
        super(clazz, -30011, scriptMsg);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -30011;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
