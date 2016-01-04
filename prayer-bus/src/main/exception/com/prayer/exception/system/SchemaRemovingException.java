package com.prayer.exception.system;

import com.prayer.base.exception.AbstractSystemException;

/**
 * 【Checked】Error-20007：Schema从H2数据库中移除过程出错
 * 
 * @author Lang
 *
 */
public class SchemaRemovingException extends AbstractSystemException {
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
    public SchemaRemovingException(final Class<?> clazz, final String identifier) {
        super(clazz, -20007, identifier);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -20007;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
