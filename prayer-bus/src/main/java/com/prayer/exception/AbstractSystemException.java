package com.prayer.exception;

import static com.prayer.util.sys.Error.error;

/**
 * System exception, describe invalid system definition
 * @author Lang
 * @see
 */
public abstract class AbstractSystemException extends AbstractException {
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
			final Object... params) {
		this(error(clazz, errorCode, params));
	}

	// ~ Abstract Methods ====================================
}
