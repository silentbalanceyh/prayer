package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class BTColumnNotExistingException extends AbstractSchemaException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -3051875057739290714L;
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
    public BTColumnNotExistingException(final Class<?> clazz, final String table, final String column){
        super(clazz, -10028, table, column);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public int getErrorCode(){
        return -10028;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
