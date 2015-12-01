package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10013：子表定义不匹配异常
 * 
 * @author Lang
 * @see
 */
public class SubtableWrongException extends AbstractSchemaException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -5790628175272226511L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     */
    public SubtableWrongException(final Class<?> clazz) {
        super(clazz, -10013);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10013;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
