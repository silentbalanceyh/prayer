package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10026：所有policy中的主键的nullable必须是false
 * 
 * @author Lang
 *
 */
public class PKNullableConflictException extends AbstractSchemaException {

    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8852539655946915968L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param attr
     */
    public PKNullableConflictException(final Class<?> clazz, final String attr) {
        super(clazz, -10026, attr);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10026;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
