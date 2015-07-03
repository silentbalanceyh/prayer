package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10008：出现数据库列重复错误
 * 
 * @author Lang
 * @see
 */
public class DuplicatedColumnException extends AbstractSchemaException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 7131993229182723906L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param table
	 * @param column
	 */
	public DuplicatedColumnException(final Class<?> clazz, final String column) {
		super(clazz, -10008, column);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10008;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
