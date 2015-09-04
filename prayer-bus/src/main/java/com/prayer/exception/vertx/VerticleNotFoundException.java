package com.prayer.exception.vertx;

import com.prayer.exception.AbstractVertXException;

/**
 * 
 * @author Lang
 *
 */
public class VerticleNotFoundException extends AbstractVertXException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 6146365932454966772L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public VerticleNotFoundException(final Class<?> clazz, final String className) {
		super(clazz, -15002, className);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -15002;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
