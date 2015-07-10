package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10019：字段__keys__中的定义每一个Key的name和category必须对应
 * 
 * @author Lang
 * @see
 */
public class KeysNameSpecificationException extends AbstractSchemaException {

	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696175747097721244L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 */
	public KeysNameSpecificationException(final Class<?> clazz,
			final String name, final String category) {
		super(clazz, -10019, name, category);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10019;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
