package com.prayer.exception.h2;

import com.prayer.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 */
public class MapperClassNullException extends AbstractTransactionException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -3306055554527891091L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public MapperClassNullException(final Class<?> clazz, final String className) {
		super(clazz, -16002, className);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -16002;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
