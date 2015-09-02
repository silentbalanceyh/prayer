package com.prayer.exception.vertx;

import com.prayer.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 */
public class DataAccessException extends AbstractTransactionException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -879292474363781703L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public DataAccessException(final Class<?> clazz, final String process) {
		super(clazz, -15001, process);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode() {
		return -15001;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
