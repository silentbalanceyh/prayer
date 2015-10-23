package com.prayer.util;

import java.util.Collection;

import com.prayer.constant.Constants;

import jodd.util.StringUtil;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class StringKit {

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param collection
	 * @param separator
	 * @return
	 */
	@NotNull
	public static String join(@NotNull @MinSize(1) final Collection<String> collection, final char separator) {
		return join(collection, separator, false);
	}

	/**
	 * 如果appendEnd为false则效果同上
	 * 
	 * @param collection
	 * @param separator
	 * @return
	 */
	@NotNull
	public static String join(@NotNull @MinSize(1) final Collection<String> collection, final char separator,
			final boolean appendEnd) {
		final StringBuilder retStr = new StringBuilder();
		if (Constants.ONE == collection.size()) {
			retStr.append(collection.iterator().next());
		} else {
			for (final String item : collection) {
				if (isNonNil(item)) {
					retStr.append(item).append(separator);
				}
			}
			if (!appendEnd) {
				retStr.deleteCharAt(retStr.length() - 1);
			}
		}
		return retStr.toString();
	}
	
	/**
	 *
	 * 
	 * @param collection
	 * @param separationString
	 * @return
	 */
	@NotNull
	public static String join(@NotNull @MinSize(1) final Collection<String> collection, final String separationString) {
		final StringBuilder retStr = new StringBuilder();
		if (Constants.ONE == collection.size()) {
			retStr.append(collection.iterator().next());
		} else {
			for (final String item : collection) {
				if (isNonNil(item)) {
					retStr.append(item).append(separationString);
				}
			}
		}
		return retStr.toString();
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean isNonNil(final String strValue) {
		boolean ret = false;
		if (null != strValue && StringUtil.isNotBlank(strValue) && StringUtil.isNotEmpty(strValue)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean isNil(final String strValue) {
		boolean ret = false;
		if (null == strValue || StringUtil.isBlank(strValue) || StringUtil.isEmpty(strValue)) {
			ret = true;
		}
		return ret;
	}

	// ~ Private Methods =====================================
	private StringKit() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
