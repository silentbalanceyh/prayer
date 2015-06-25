package com.prayer.exception;

import static com.prayer.util.sys.Error.error;

/**
 * System exception, describe invalid system definition
 *
 * @author Lang
 * @package com.prayer.exception
 * @name AbstractSystemException
 * @class com.prayer.exception.AbstractSystemException
 * @date Oct 10, 2014 4:12:56 PM
 * @see
 */
public abstract class AbstractSystemException extends RuntimeException {
	// ~ Static Fields =======================================
	/**
     *
     */
	private static final long serialVersionUID = -7714033567838757744L;

	// ~ Constructors ========================================

	/**
	 * @param message
	 */
	public AbstractSystemException(final String message) {
		super(message);
	}
	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 */
	public AbstractSystemException(final Class<?> clazz, final int errorCode,
			Object... params) {
		this(error(clazz, errorCode, params));
	}

	// ~ Abstract Methods ====================================

	/**
	 * Get type of schema exception. *
	 */
	public abstract int getErrorCode();
}
