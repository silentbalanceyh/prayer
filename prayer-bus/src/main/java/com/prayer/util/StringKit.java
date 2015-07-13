package com.prayer.util;

import java.util.Collection;

import jodd.util.StringUtil;
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
	 * @return
	 */
	public static String join(final Collection<String> collection, final String separator){
		final StringBuilder retStr = new StringBuilder();
		int idx = 0;
		for(final String item: collection){
			retStr.append(item);
			idx++;
			if(idx < collection.size() - 1){
				retStr.append(separator);
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
