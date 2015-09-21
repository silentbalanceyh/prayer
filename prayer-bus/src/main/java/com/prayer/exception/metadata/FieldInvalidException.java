package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;
/**
 * 
 * @author Lang
 *
 */
public class FieldInvalidException extends AbstractMetadataException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8391592601758189290L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param field
     * @param identifier
     */
    public FieldInvalidException(final Class<?> clazz, final String field, final String identifier){
        super(clazz, -11003, field, identifier);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11003;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
