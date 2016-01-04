package com.prayer.exception.system;

import com.prayer.base.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class MetaTypeWrongException extends AbstractSystemException {
    
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
        super(clazz, -20011, type, key, file);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -20011;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
