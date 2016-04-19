package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.resource.Resources;

/**
 * 【Checked】Error-10035：columnType读取到的内容不符合数据库规范，mapping文件有问题
 * 
 * @author Lang
 *
 */
public class DBMappingConflictException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1719429880345146812L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param columnType
     */
    public DBMappingConflictException(final Class<?> clazz, final String columnType) {
        super(clazz, -10035, columnType, Resources.Data.CATEGORY);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10035;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
