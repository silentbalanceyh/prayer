package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10010：数据库主键丢失异常
 * 
 * @author Lang
 * @see
 */
public class PKMissingException extends AbstractSchemaException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 8755562168435213908L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param table
     */
    public PKMissingException(final Class<?> clazz, final String table) {
        super(clazz, -10010, table);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10010;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
