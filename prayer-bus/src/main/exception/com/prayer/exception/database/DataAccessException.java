package com.prayer.exception.database;

import com.prayer.fantasm.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 */
public class DataAccessException extends AbstractTransactionException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -879292474363781703L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public DataAccessException(final Class<?> clazz, final String process) {
        super(clazz, -16001, process);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -16001;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
