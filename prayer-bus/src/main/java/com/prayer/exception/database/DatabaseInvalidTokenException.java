package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
public class DatabaseInvalidTokenException extends AbstractDatabaseException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -9180541362343699707L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param url
     * @param username
     * @param password
     */
    public DatabaseInvalidTokenException(final Class<?> clazz, final String url, final String username,
            final String password) {
        super(clazz, -11017, url, username, password);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -11017;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
