package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10003：存在属性的Pattern不匹配
 * 
 * @author Lang
 * @see
 */
public class PatternNotMatchException extends AbstractSchemaException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 8553345772551092248L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 * @param value
	 * @param pattern
	 */
	public PatternNotMatchException(final Class<?> clazz, final String attr, final String value, final String pattern) {
		super(clazz, -10003, attr, value, pattern);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10003;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
