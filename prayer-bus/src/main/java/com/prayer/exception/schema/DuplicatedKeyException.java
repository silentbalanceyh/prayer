package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10018：键重复异常：1.如果__keys__中出现了重复的Key，则抛出该异常
 * 
 * @author Lang
 * @see
 */
public class DuplicatedKeyException extends AbstractSchemaException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 3612184415275276817L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param key
     */
    public DuplicatedKeyException(final Class<?> clazz, final String key) {
        super(clazz, -10018, key);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10018;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
