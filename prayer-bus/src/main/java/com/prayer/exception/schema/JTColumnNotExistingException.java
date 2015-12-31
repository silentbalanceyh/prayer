package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 当外键引用了当前Schema文件，列名不存在
 * @author Lang
 *
 */
public class JTColumnNotExistingException extends AbstractSchemaException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -5695801031002637023L;
    
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
    public JTColumnNotExistingException(final Class<?> clazz, final String table, final String column){
        super(clazz, -10031, table, column);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode(){
        return -10031;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
