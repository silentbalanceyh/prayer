package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class ValidatorInvalidException extends AbstractWebException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 2114841491912840885L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public ValidatorInvalidException(final Class<?> clazz, final String className) {
		super(clazz, -30006, className);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -30006;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
