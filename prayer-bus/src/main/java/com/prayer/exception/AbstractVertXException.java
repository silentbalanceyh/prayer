package com.prayer.exception;

import static com.prayer.util.Error.error;

/**
 * 和VertX相关的所有异常信息集合，H2中涉及到EVX前缀表
 * 
 * @author Lang
 *
 */
public abstract class AbstractVertXException extends AbstractException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -362465592359952752L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractVertXException(final String message) {
		super(message);
	}

	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 */
	public AbstractVertXException(final Class<?> clazz, final int errorCode, final Object... params) {
		this(error(clazz, errorCode, params));
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
