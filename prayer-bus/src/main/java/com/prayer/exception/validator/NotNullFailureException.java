package com.prayer.exception.validator;

import com.prayer.exception.AbstractMetadataException;

/**
 * nullable为false的时候的验证失败异常，即不可为null，空串
 * @author Lang
 *
 */
public class NotNullFailureException extends AbstractMetadataException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 8935013215811274638L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public NotNullFailureException(final Class<?> clazz, final String field){
		super(clazz, -12002, field);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode(){
		return -12002;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
