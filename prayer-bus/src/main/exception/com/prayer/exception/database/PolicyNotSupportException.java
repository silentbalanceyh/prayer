package com.prayer.exception.database;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 *
 */
public class PolicyNotSupportException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -8786429327757360374L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param policy
     */
    public PolicyNotSupportException(final Class<?> clazz, final MetaPolicy policy){
        super(clazz, -11014, policy.toString());
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11014;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
