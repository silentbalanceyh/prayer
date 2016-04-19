package com.prayer.exception.launcher;

import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
public class ParameterMissedException extends AbstractLauncherException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6717757527824124434L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param param
     * @param file
     */
    public ParameterMissedException(final Class<?> clazz, final String param, final String file){
        super(clazz,-40001,param,file);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -40001;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
