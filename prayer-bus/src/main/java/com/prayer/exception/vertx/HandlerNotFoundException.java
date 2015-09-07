package com.prayer.exception.vertx;

import com.prayer.exception.AbstractVertXException;

/**
 * 
 * @author Lang
 *
 */
public class HandlerNotFoundException extends AbstractVertXException {
	// ~ Static Fields =======================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7762931217238707415L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public HandlerNotFoundException(final Class<?> clazz, final String className) {
		super(clazz, -15004, className);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -15004;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
