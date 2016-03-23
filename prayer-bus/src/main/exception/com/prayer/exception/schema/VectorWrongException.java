package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10036：type的定义和columnType冲突
 * 
 * @author Lang
 *
 */
public class VectorWrongException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1860835903181780691L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param values
     * @param type
     * @param columnType
     */
    public VectorWrongException(final Class<?> clazz, final String values, final String type, final String columnType) {
        super(clazz, -10036, type, values, columnType);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10036;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
