package com.prayer.exception.launcher;

import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
public class NumericValueException extends AbstractLauncherException {
    /**
     * 
     */
    private static final long serialVersionUID = 8039389539570697040L;

    // ~ Static Fields =======================================
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
    public NumericValueException(final Class<?> clazz, final String param, final String value, final String file) {
        super(clazz, -40003, param, value, file);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -40003;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
