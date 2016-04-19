package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.resource.Resources;

/**
 * 【Checked】Error-10038：数据库改变列类型抛出此异常信息
 * 
 * @author Lang
 *
 */
public class DBColumnTypeUpdatingException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 834588167442602742L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param field
     * @param oldType
     * @param newType
     */
    public DBColumnTypeUpdatingException(final Class<?> clazz, final String field, final String oldType,
            final String newType) {
        super(clazz, -10038, field, oldType, newType, Resources.Data.CATEGORY);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public int getErrorCode() {
        return -10038;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
