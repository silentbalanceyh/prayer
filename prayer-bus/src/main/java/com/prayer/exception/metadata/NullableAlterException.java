package com.prayer.exception.metadata;

import com.prayer.exception.AbstractDatabaseException;

/**
 * 【Checked】-11001，将一个字段从NULL改成NOT NULL时因为该字段本身有null值，所以不可更改
 * @author Lang
 *
 */
public class NullableAlterException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -8643000244105817309L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param column
     * @param table
     */
    public NullableAlterException(final Class<?> clazz, final String column, final String table){
        super(clazz, -11001, column, table);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -11001;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
