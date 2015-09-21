package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;

/**
 * 特殊属性验证的时候其类型必须匹配，不匹配的验证会抛出该异常
 * @author Lang
 *
 */
public class ValidatorConflictException extends AbstractMetadataException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -3730941267199352106L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public ValidatorConflictException(final Class<?> clazz, final String type, final String attribute){
        super(clazz, -11012, type, attribute);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11012;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
