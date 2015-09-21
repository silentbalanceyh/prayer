package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;
/**
 * 
 * @author Lang
 *
 */
public class ExecuteFailureException extends AbstractMetadataException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -7232574068223095914L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     */
    public ExecuteFailureException(final Class<?> clazz){
        super(clazz, -11009);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11009;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
