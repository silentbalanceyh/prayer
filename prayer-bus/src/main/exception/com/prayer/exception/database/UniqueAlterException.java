package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * -11019，将一个字段改成UNIQUE
 * 
 * @author Lang
 *
 */
public class UniqueAlterException extends AbstractDatabaseException {

    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 7091770608613169880L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param column
     * @param table
     */
    public UniqueAlterException(final Class<?> clazz, final String column, final String table) {
        super(clazz, -11019, column, table);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -11019;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
