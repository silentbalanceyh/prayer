package com.prayer.exception.vertx;

import com.prayer.exception.AbstractVertXException;

/**
 * 
 * @author Lang
 *
 */
public class HandlerInvalidException extends AbstractVertXException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -2010931551754448288L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public HandlerInvalidException(final Class<?> clazz, final String className) {
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
