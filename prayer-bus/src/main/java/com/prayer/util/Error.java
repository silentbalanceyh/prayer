package com.prayer.util;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.prayer.constant.Resources;
import com.prayer.exception.AbstractException;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Error { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * Error property loader to read Error Message
	 */
	private static PropertyKit loader = new PropertyKit(Error.class, Resources.ERR_CODE_FILE);
	// instance(PropertyKit.class.getName(), Error.class,
	// Resources.ERR_PROP_FILE);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(@Max(-10000) final int errorCode, final Object... params) {
		return error(null, errorCode, params);
	}

	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(@NotNull final Class<?> clazz, @Max(-10000) final int errorCode,
			final Object... params) {
		return message(clazz, 'E', errorCode, params);
	}

	/**
	 * 
	 * @param logger
	 * @param clazz
	 * @param errKey
	 * @param exp
	 * @param params
	 */
	public static void debug(@NotNull final Logger logger, @NotNull final Class<?> clazz, final String errKey,
			@NotNull final AbstractException exp, final Object... params) {
		if (StringKit.isNil(errKey)) {
			debug(logger, clazz, exp, params);
		} else {
			final StringBuilder errMsg = new StringBuilder("[D] ==> ");
			errMsg.append(message(clazz, errKey, exp.getErrorCode(), params));
			if (logger.isDebugEnabled()) {
				logger.debug(errMsg.toString(), exp);
			}
		}
	}

	/**
	 * 直接输出信息
	 * 
	 * @param logger
	 * @param errKey
	 * @param params
	 */
	public static void debug(@NotNull final Logger logger, @NotNull final String errKey, final Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(message(errKey, params));
		}
	}

	/**
	 * 
	 * @param logger
	 * @param errKey
	 * @param exp
	 * @param params
	 */
	public static void debug(@NotNull final Logger logger, @NotNull final String errKey, @NotNull final Throwable exp,
			final Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(message(errKey, params), exp);
		}
	}

	/**
	 * 
	 * @param logger
	 * @param message
	 */
	public static void info(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String message) {
		info(logger, message, null);
	}

	/**
	 * 
	 * @param logger
	 * @param message
	 * @param exp
	 */
	public static void info(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String message,
			final Throwable exp) {
		if (logger.isDebugEnabled()) {
			if (null == exp) {
				logger.debug(message);
			} else {
				logger.debug(message, exp);
			}
		} else if (logger.isInfoEnabled()) {
			if (null == exp) {
				logger.info(message);
			} else {
				logger.info(message, exp);
			}
		}
	}

	/**
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String message(@NotNull @NotBlank @NotEmpty final String key, final Object... params) {
		return null == loader ? "" : MessageFormat.format(loader.getString(key), params);
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private static String message(final Class<?> clazz, final char prefix, final int errorCode, // NOPMD
			final Object... params) {
		// Error Code Key in property file.
		final String errKey = prefix + String.valueOf(Math.abs(errorCode));
		return message(clazz, errKey, errorCode, params);
	}

	private static String message(final Class<?> clazz, final String errKey, final int errorCode,
			final Object... params) {
		// Error message generation
		final StringBuilder errMsg = new StringBuilder(32);
		errMsg.append("[ERR").append(errorCode).append(']');
		if (null != clazz) {
			errMsg.append(" Class -> " + clazz.getName() + " |");
		}
		errMsg.append(' ').append(MessageFormat.format(loader.getString(errKey), params));
		return errMsg.toString();
	}

	/**
	 * 
	 * @param logger
	 * @param clazz
	 * @param exp
	 * @param params
	 */
	private static void debug(final Logger logger, final Class<?> clazz, final AbstractException exp, // NOPMD
			final Object... params) {
		if (logger.isDebugEnabled()) {
			final StringBuilder errMsg = new StringBuilder("[D] ==> ");
			errMsg.append(message(clazz, 'D', exp.getErrorCode(), params));
			logger.debug(errMsg.toString(), exp);
		}
	}

	private Error() {
	}
	// ~ hashCode,equals,toString ============================

}
