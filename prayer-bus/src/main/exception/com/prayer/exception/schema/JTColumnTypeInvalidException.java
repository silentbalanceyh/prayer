package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class JTColumnTypeInvalidException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    
    /**
     * 
     */
    private static final long serialVersionUID = -2092080801318018970L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param table
     * @param column
     * @param type
     */
    public JTColumnTypeInvalidException(final Class<?> clazz, final String table, final String column,
            final String type) {
        super(clazz, -10033, table, column, type);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10033;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
