package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10011：数据库主键策略异常
 * 
 * @author Lang
 * @see
 */
public class PKPolicyConflictException extends AbstractSchemaException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -2387879119047725338L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param table
     */
    public PKPolicyConflictException(final Class<?> clazz, final String policy, final String table) {
        super(clazz, -10011, policy, table);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10011;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
