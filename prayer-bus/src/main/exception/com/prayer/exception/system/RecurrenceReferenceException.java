package com.prayer.exception.system;

import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class RecurrenceReferenceException extends AbstractSystemException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 1131965148651299898L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param folder
     * @param pattern
     */
    public RecurrenceReferenceException(final Class<?> clazz, final String folder, final String pattern) {
        super(clazz, -20006, folder, pattern);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -20006;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
