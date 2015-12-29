package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class BFKConstraintInvalidException extends AbstractSchemaException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8445167823422155258L;
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
    public BFKConstraintInvalidException(final Class<?> clazz, final String table, final String column){
        super(clazz, -10029, table, column);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -10029;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
