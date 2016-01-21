package com.prayer.exception.database;

import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 调用方法出现了Policy不匹配的情况
 * @author Lang
 *
 */
public class PolicyConflictCallException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 4020476570010818829L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param policy
     */
    public PolicyConflictCallException(final Class<?> clazz, final String policy){
        super(clazz,-11005,policy);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11005;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
