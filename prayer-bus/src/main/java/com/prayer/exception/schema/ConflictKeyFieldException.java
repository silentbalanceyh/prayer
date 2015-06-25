package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10019：字段__fields__和__keys__定义冲突
 * 
 * @author Lang
 * @see
 */
public class ConflictKeyFieldException extends AbstractSchemaException {

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
	public ConflictKeyFieldException(final Class<?> clazz, final String attr) {
		super(clazz, -10017, attr);
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
