package com.prayer.exception.system;

import com.prayer.exception.AbstractSystemException;

/**
 * 【Checked】Error-20006：Schema在系统中不存在
 * 
 * @author Lang
 *
 */
public class SchemaNotFoundException extends AbstractSystemException {
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
        super(clazz, -20006, identifier);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -20006;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
