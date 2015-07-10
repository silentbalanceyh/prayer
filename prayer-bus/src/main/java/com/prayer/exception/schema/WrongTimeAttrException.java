package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10024：特殊条件下key=value的出现次数必须固定而且对
 * 
 * @author Lang
 *
 */
public class WrongTimeAttrException extends AbstractSchemaException {

	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -6082283218777681980L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 * @param value
	 * @param expected
	 * @param actual
	 * @param keyCategory
	 */
	public WrongTimeAttrException(final Class<?> clazz, final String attr,
			final String value, final String expected, final String actual, final String keyCategory) {
		super(clazz, -10024, attr, value, expected, actual, keyCategory);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10024;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
