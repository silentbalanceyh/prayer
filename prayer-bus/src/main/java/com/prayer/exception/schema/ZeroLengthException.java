package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10006：Array类型Json节点长度为0异常 1.如果Array类型的Json节点中没有任何元素则抛出该异常
 * 
 * @author Lang
 * @see
 */
public class ZeroLengthException extends AbstractSchemaException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -2997975750048958161L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param attr
     */
    public ZeroLengthException(final Class<?> clazz, final String attr) {
        super(clazz, -10006, attr);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10006;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
