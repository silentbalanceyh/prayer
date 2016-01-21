package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * Target Column中的数据类型不合法
 * 
 * @author Lang
 *
 */
public class BTColumnTypeInvalidException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -466725592930810822L;

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
    public BTColumnTypeInvalidException(final Class<?> clazz, final String table, final String column,
            final String type) {
        super(clazz, -10030, table, column, type);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10030;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
