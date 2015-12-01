package com.prayer.exception.system;

import com.prayer.base.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class MetadataDefMissingException extends AbstractSystemException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 3123459193285312461L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param file
     * @param key
     */
    public MetadataDefMissingException(final Class<?> clazz, final String file, final String key){
        super(clazz, -20010, file, key);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -20010;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
