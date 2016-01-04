package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;
/**
 * 
 * @author Lang
 *
 */
public class ColumnInvalidException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 732279106869851763L;
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
    public ColumnInvalidException(final Class<?> clazz, final String column, final String table){
        super(clazz, -11010, column, table);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11010;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
