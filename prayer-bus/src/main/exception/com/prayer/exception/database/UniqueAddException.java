package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * 【Checked】-11018，在数据行超过1行的时候，不可以添加UNIQUE字段
 * @author Lang
 *
 */
public class UniqueAddException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -2559917735809203929L;
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
    public UniqueAddException(final Class<?> clazz, final String column, final String table){
        super(clazz, -11018, column, table);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -11018;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
