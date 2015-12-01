package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10023：columns中属性在__fields__中丢失：1.
 * 如果columns中的列在__fields__中未定义抛出该异常
 * 
 * @author Lang
 *
 */
public class ColumnsMissingException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -6862149397933201262L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param column
     * @param keyName
     */
    public ColumnsMissingException(final Class<?> clazz, final String column, final String keyName) {
        super(clazz, -10023, column, keyName);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public int getErrorCode() {
        return -10023;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
