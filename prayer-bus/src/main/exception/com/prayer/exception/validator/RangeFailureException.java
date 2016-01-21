package com.prayer.exception.validator;

import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * min, max两个属性的异常信息
 * 
 * @author Lang
 *
 */
public class RangeFailureException extends AbstractDatabaseException {
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
    public RangeFailureException(final Class<?> clazz, final String flag, final String field, final String range,
            final String value) {
        super(clazz, -12004, flag, field, range, value);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -12004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
