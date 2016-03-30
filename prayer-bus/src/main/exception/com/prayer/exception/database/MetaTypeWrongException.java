package com.prayer.exception.database;

import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
public class MetaTypeWrongException extends AbstractDatabaseException {
    
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 7844836777950191569L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param type
     * @param key
     * @param file
     */
    public MetaTypeWrongException(final Class<?> clazz, final String type, final String file, final String key) {
        super(clazz, -11022, type, key, file);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -11022;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
