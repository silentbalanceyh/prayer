package com.prayer.exception.web;

import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
public class SpecialDataTypeException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8488944115046139528L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param type
     * @param value
     */
    public SpecialDataTypeException(final Class<?> clazz, final DataType type, final String value) {
        super(clazz, -30018, type.toString(), value);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30018;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
