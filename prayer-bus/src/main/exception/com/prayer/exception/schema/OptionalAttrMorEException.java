package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10004：可选属性丢失异常
 * 
 * @author Lang
 * @see
 */
public class OptionalAttrMorEException extends AbstractSchemaException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 1923424849044814990L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param attr
     */
    public OptionalAttrMorEException(final Class<?> clazz, final String attr, final String category) {
        super(clazz, -10004, attr, category);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
