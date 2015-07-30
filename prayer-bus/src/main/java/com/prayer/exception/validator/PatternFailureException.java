package com.prayer.exception.validator;

import com.prayer.exception.AbstractMetadataException;

/**
 * 验证pattern属性出错时的特殊异常
 * @author Lang
 *
 */
public class PatternFailureException extends AbstractMetadataException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -949243943073507813L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public PatternFailureException(final Class<?> clazz, final String value, final String field, final String pattern){
		super(clazz, -12001, value, field, pattern);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode(){
		return -12001;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
