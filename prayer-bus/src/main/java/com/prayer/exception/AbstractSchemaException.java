package com.prayer.exception;

/**
 * Schema验证的抽象类，主要用于验证Schema是否合法相关信息，参考EA文件
 * @author Lang
 * @package com.prayer.exception
 * @name AbstractSchemaException
 * @class com.prayer.exception.AbstractSchemaException
 * @date Oct 10, 2014 4:13:21 PM
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

	// ~ Abstract Methods ====================================

	/**
	 * Get type of schema exception. *
	 */
	public abstract int getErrorCode();
}
