package com.prayer.exception.validator;

import com.prayer.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
public class PrecisionFailureException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 3394477319420070931L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PrecisionFailureException(final Class<?> clazz, final String field, final String length,
            final String precision, final String value) {
        super(clazz, -12005, field, length, precision, value);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -12005;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
