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
		return format(clazz, errorKey('E', errorCode), params);
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
			final AbstractException exp, final Object... params) {
		final StringBuilder errMsg = new StringBuilder("[D] ==> ");
		if (logger.isDebugEnabled()) {
			if (StringKit.isNil(errKey)) {
				errMsg.append(format(clazz, errorKey('D', exp.getErrorCode()), params));
				logger.debug(errMsg.toString(), exp);
			} else {
				errMsg.append(format(clazz, errKey, exp.getErrorCode(), params));
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
		debug(logger, errKey, null, params);
	}

	/**
	 * 
	 * @param logger
	 * @param errKey
	 * @param exp
	 * @param params
	 */
	public static void debug(@NotNull final Logger logger, @NotNull final String errKey, final Throwable exp,
			final Object... params) {
		if (logger.isDebugEnabled()) {
			if (null == exp) {
				logger.debug(message(errKey, params), params);
			} else {
				logger.debug(message(errKey, params), params, exp);
			}
		}
	}
	
	/**
	 * 
	 * @param logger
	 * @param message
	 */
	public static void debug(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String message){
		if(logger.isDebugEnabled()){
			logger.debug(message);
		}
	}
	/**
	 * 
	 * @param logger
	 * @param errKey
	 * @param exp
	 * @param params
	 */
	public static void info(@NotNull final Logger logger, @NotNull final String errKey, final Throwable exp,final Object... params){
		if (logger.isInfoEnabled()) {
			if (null == exp) {
				logger.info(message(errKey, params), params);
			} else {
				logger.info(message(errKey, params), params, exp);
			}
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
		if (logger.isInfoEnabled()) {
			if (null == exp) {
				logger.info(message);
			} else {
				logger.info(message, exp);
			}
		}
	}

	/**
	 * 直接从属性文件中提取Patterns，然后根据参数执行格式化
	 * 
	 * @param errKey
	 * @param params
	 * @return
	 */
	public static String message(@NotNull final String errKey, final Object... params) {
		return null == loader ? "" : MessageFormat.format(loader.getString(errKey), params);
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private static String errorKey(final char prefix, final int errorCode) {
		return prefix + String.valueOf(Math.abs(errorCode));
	}

	private static String format(final Class<?> clazz, final String errKey, final Object... params) {
		final StringBuilder errMsg = new StringBuilder(32);
		errMsg.append('[').append(errKey).append(']');
		if (null != clazz) {
			errMsg.append(" Class -> " + clazz.getName() + " |");
		}
		errMsg.append(' ').append(message(errKey, params));
		return errMsg.toString();
	}

	private Error() {
	}
	// ~ hashCode,equals,toString ============================

}
