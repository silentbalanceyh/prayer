package com.prayer.exception.validator;

import com.prayer.exception.AbstractDatabaseException;

/**
 * maxLength, minLength两个属性的异常信息
 * 
 * @author Lang
 *
 */
public class LengthFailureException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6767768954300142543L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public LengthFailureException(final Class<?> clazz, final String flag, final String field, final String length,
            final String value) {
        super(clazz, -12003, flag, field, length, value);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -12003;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
