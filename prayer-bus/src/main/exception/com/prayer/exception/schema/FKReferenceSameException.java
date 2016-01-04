package com.prayer.exception.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class FKReferenceSameException extends AbstractSchemaException {
    // ~ Static Fields =======================================
    
    /**
     * 
     */
    private static final long serialVersionUID = -6978892758014531484L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param fkName
     * @param table
     */
    public FKReferenceSameException(final Class<?> clazz, final String fkName, final String table) {
        super(clazz, -10034, fkName, table);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public int getErrorCode(){
        return -10034;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
