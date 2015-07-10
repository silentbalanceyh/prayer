package com.prayer.util.sys;

import java.util.Set;

import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Converter {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Converter.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param sets
	 * @return
	 */
	public static String toStr(@MinSize(1) final Set<String> sets) {
		return toStr(sets.toArray(new String[] {}));
	}

	/**
	 * 
	 * @param setArr
	 * @return
	 */
	public static String toStr(@MinLength(1) final String... setArr) {
		final StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < setArr.length; i++) {
			retStr.append(setArr[i]);
			if (i < setArr.length - 1) {
				retStr.append(',');
			}
		}
		return retStr.toString();
	}

	/**
	 * 
	 * @param clazz
	 * @param inputStr
	 * @return
	 */
	public static <T extends Enum<T>> T fromStr(@NotNull final Class<T> clazz,
			@NotNull @NotBlank @NotEmpty final String inputStr) {
		T retEnum = null;
		try {
			retEnum = Enum.valueOf(clazz, inputStr);
		} catch (IllegalArgumentException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Enum value invalid: " + inputStr, ex);
			}
		}
		return retEnum;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Converter() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
