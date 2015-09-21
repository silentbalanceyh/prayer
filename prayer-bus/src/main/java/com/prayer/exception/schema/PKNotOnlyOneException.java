package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10009：__keys__中多个主键异常
 * 
 * @author Lang
 * @see
 */
public class PKNotOnlyOneException extends AbstractSchemaException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 6488232748226409446L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param dataType
     */
    public PKNotOnlyOneException(final Class<?> clazz) {
        super(clazz, -10009);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10009;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
