package com.prayer.exception.database;

import static com.prayer.util.Converter.toStr;

import java.util.Set;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * 当前的Metadata Server不是SQL的，在初始化iBatis连接池时出错
 * 
 * @author Lang
 *
 */
public class MetadataNotSupportException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 9195033210472085460L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param database
     * @param expected
     */
    public MetadataNotSupportException(final Class<?> clazz, final String database, final Set<String> expected) {
        super(clazz, -11020, database, toStr(expected));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -11020;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
