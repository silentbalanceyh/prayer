package com.prayer.util.sys;

import static com.prayer.util.sys.Instance.instance;

import java.text.MessageFormat;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import com.prayer.res.cv.Resources;
import com.prayer.util.PropertyKit;

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
	 * @param clazz
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static String error(@NotNull final Class<?> clazz,
			@Max(-10000) final int errorCode, final Object... params) {
		// Error Code Key in property file.
		final String errKey = "E" + Math.abs(errorCode);
		// Error message generation
		final StringBuilder errMsg = new StringBuilder();
		errMsg.append("[ERR").append(errorCode).append(']');
		if (null != clazz) {
			errMsg.append(" Class -> " + clazz.getName() + " |");
		}
		errMsg.append(' ').append(
				MessageFormat.format(loader.getString(errKey), params));
		return errMsg.toString();
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
