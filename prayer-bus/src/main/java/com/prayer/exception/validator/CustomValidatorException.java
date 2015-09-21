package com.prayer.exception.validator;

import com.prayer.exception.AbstractMetadataException;

/**
 * 
 * @author Lang
 *
 */
public class CustomValidatorException extends AbstractMetadataException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6815396914443294901L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public CustomValidatorException(final Class<?> clazz, final String validator) {
        super(clazz, -12006, validator);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -12006;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
