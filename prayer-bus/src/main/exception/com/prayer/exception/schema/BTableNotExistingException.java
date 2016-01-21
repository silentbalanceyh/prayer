package com.prayer.exception.schema;

import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * Business Table不存在：
 * <code>外键验证</code>
 * <code>子表验证</code>
 * @author Lang
 *
 */
public class BTableNotExistingException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6604745846994657786L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param table
     */
    public BTableNotExistingException(final Class<?> clazz, final String table){
        super(clazz, -10027, table);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -10027;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
