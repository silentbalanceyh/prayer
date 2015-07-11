package com.prayer.exception;
/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractException extends Exception{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 3478027283838490966L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * @param message
	 */
	public AbstractException(final String message) {
		super(message);
	}
	// ~ Abstract Methods ====================================
	/**
	 * Get type of schema exception. *
	 */
	public abstract int getErrorCode();
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
