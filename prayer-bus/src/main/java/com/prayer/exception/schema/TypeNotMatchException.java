package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10016：属性数据类型不匹配
 * 
 * @author Lang
 * @see
 */
public class TypeNotMatchException extends AbstractSchemaException {

	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916601885916494445L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attrName
	 * @param dataType
	 */
	public TypeNotMatchException(final Class<?> clazz, final String attrName,
			final String dataType) {
		super(clazz, -10016, attrName, dataType);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10016;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
