package com.prayer.util.sys;

import static com.prayer.util.sys.Instance.instance;

import java.text.MessageFormat;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import org.slf4j.Logger;

import com.prayer.exception.AbstractException;
import com.prayer.res.cv.Resources;
import com.prayer.util.PropertyKit;
import com.prayer.util.StringKit;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Error {
	// ~ Static Fields =======================================
	/**
	 * Error property loader to read Error Message
	 */
	private static PropertyKit loader = instance(PropertyKit.class.getName(),
			Error.class, Resources.ERR_PROP_FILE);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(@Max(-10000) final int errorCode,
			final Object... params) {
		return error(null, errorCode, params);
	}

	/**
	 * 
	 * @param logger
	 * @param clazz
	 * @param errKey
	 * @param exp
	 * @param params
	 */
	public static void debug(@NotNull final Logger logger,
			@NotNull final Class<?> clazz, final String errKey,
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
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(@NotNull final Class<?> clazz,
			@Max(-10000) final int errorCode, final Object... params) {
		return message(clazz, 'E', errorCode, params);
	}

	private static String message(final Class<?> clazz, final char prefix,
			final int errorCode, final Object... params) {
		// Error Code Key in property file.
		final String errKey = prefix + String.valueOf(Math.abs(errorCode));
		return message(clazz, errKey, errorCode, params);
	}

	private static String message(final Class<?> clazz, final String errKey,
			final int errorCode, final Object... params) {
		// Error message generation
		final StringBuilder errMsg = new StringBuilder(32);
		errMsg.append("[ERR").append(errorCode).append(']');
		if (null != clazz) {
			errMsg.append(" Class -> " + clazz.getName() + " |");
		}
		errMsg.append(' ').append(
				MessageFormat.format(loader.getString(errKey), params));
		return errMsg.toString();
	}

	/**
	 * 
	 * @param logger
	 * @param clazz
	 * @param exp
	 * @param params
	 */
	private static void debug(final Logger logger,	// NOPMD
			final Class<?> clazz,
			final AbstractException exp, final Object... params) {
		if (logger.isDebugEnabled()) {
			final StringBuilder errMsg = new StringBuilder("[D] ==> ");
			errMsg.append(message(clazz, 'D', exp.getErrorCode(), params));
			logger.debug(errMsg.toString(), exp);
		}
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private Error() {
	}
	// ~ hashCode,equals,toString ============================

}
