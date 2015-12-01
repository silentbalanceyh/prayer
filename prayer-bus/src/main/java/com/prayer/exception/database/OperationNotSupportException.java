package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
public class OperationNotSupportException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 1057256741672334352L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param method
     */
    public OperationNotSupportException(final Class<?> clazz, final String method) {
        super(clazz, -11015, method, clazz.getName());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -11015;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
