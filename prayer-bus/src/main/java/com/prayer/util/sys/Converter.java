package com.prayer.util.sys;

import java.util.Set;

/**
 * 
 * @author Lang
 * @see
 */
public final class Converter {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param sets
	 * @return
	 */
	public static String toStr(final Set<String> sets) {
		return toStr(sets.toArray(new String[] {}));
	}

	/**
	 * 
	 * @param setArr
	 * @return
	 */
	public static String toStr(final String... setArr) {
		final StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < setArr.length; i++) {
			retStr.append(setArr[i]);
			if (i < setArr.length - 1) {
				retStr.append(',');
			}
		}
		return retStr.toString();
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
