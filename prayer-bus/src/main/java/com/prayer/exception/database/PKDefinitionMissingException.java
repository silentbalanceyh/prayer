package com.prayer.exception.database;

import com.prayer.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
public class PKDefinitionMissingException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    
    /**
     * 
     */
    private static final long serialVersionUID = 1190227770246271408L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param identifier
     */
    public PKDefinitionMissingException(final Class<?> clazz, final String identifier){
        super(clazz,-11013,identifier);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11013;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
