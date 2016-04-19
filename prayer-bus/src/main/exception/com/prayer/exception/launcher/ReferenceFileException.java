package com.prayer.exception.launcher;

import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
public class ReferenceFileException extends AbstractLauncherException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 5739642186238591727L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param refFile
     * @param configFile
     */
    public ReferenceFileException(final Class<?> clazz, final String refFile, final String configFile) {
        super(clazz, -40002, refFile, configFile);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -40002;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
