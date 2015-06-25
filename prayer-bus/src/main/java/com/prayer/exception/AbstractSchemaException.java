package com.prayer.exception;

import static com.prayer.util.sys.Error.error;

/**
 * Schema验证的抽象类，主要用于验证Schema是否合法相关信息，参考EA文件
 * @author Lang
 * @see
 */
public abstract class AbstractSchemaException extends Exception {
	// ~ Static Fields =======================================
	/**
     *
     */
	private static final long serialVersionUID = 9007713506522349143L;

	// ~ Constructors ========================================

	/**
	 * @param message
	 */
	public AbstractSchemaException(final String message) {
		super(message);
	}

	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 */
	public AbstractSchemaException(final Class<?> clazz, final int errorCode,
			final Object... params) {
		this(error(clazz, errorCode, params));
	}

	// ~ Abstract Methods ====================================

	/**
	 * Get type of schema exception. *
	 */
	public abstract int getErrorCode();
	// ~ Methods =============================================
}
