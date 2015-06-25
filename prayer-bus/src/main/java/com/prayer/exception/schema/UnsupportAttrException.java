package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10017：不支持的属性异常
 * 
 * @author Lang
 * @see
 */
public class UnsupportAttrException extends AbstractSchemaException {

	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 7414619032332865858L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 */
	public UnsupportAttrException(final Class<?> clazz, final String attr) {
		super(clazz, -10017, attr);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10017;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
