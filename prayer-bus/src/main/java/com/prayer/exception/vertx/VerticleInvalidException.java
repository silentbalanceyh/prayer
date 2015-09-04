package com.prayer.exception.vertx;

import com.prayer.exception.AbstractVertXException;

/**
 * 
 * @author Lang
 *
 */
public class VerticleInvalidException extends AbstractVertXException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -4366761762961024451L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public VerticleInvalidException(final Class<?> clazz, final String className) {
		super(clazz, -15003, className);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -15003;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
