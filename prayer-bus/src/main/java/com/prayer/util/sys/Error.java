package com.prayer.util.sys;

import static com.prayer.util.sys.Instance.singleton;

import java.text.MessageFormat;

import com.prayer.res.cv.Resources;
import com.prayer.util.PropertyKit;

/**
 * 
 * @author Lang
 * @see
 */
public final class Error {
	// ~ Static Fields =======================================
	/**
	 * Error property loader to read Error Message
	 */
	private static PropertyKit loader = singleton(PropertyKit.class,
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
	public static String error(final int errorCode, Object... params) {
		return error(null, errorCode, params);
	}

	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(final Class<?> clazz, final int errorCode,
			Object... params) {
		// Error Code Key in property file.
		final String errKey = "E" + Math.abs(errorCode);
		// Error message generation
		final StringBuilder errMsg = new StringBuilder();
		errMsg.append("[E").append(errorCode).append(']');
		if (null != clazz) {
			errMsg.append(" Class -> |");
		}
		errMsg.append(' ');
		// Extract message from pattern and parameters
		errMsg.append(MessageFormat.format(loader.getString(errKey), params));
		return errMsg.toString();
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
