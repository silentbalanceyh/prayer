package com.prayer.exception.database;

import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 【Checked】Error-11024：Schema在系统中不存在
 * 
 * @author Lang
 *
 */
public class SchemaNotFoundException extends AbstractDatabaseException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -2587298477734592516L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public SchemaNotFoundException(final Class<?> clazz, final String identifier) {
        super(clazz, -11024, identifier);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -11024;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
