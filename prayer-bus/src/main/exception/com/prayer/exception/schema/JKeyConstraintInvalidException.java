package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class JKeyConstraintInvalidException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -4242778108096227453L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param table
     * @param column
     */
    public JKeyConstraintInvalidException(final Class<?> clazz, final String table, final String column){
        super(clazz, -10032, table, column);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -10032;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
