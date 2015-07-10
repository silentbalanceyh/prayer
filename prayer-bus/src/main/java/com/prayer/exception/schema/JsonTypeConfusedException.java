package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10002：Json类型不匹配异常（Array/Object）
 *  1.如果Json节点应该是Object，但类型是非Object则抛出此异常
 *  2.如果Json节点应该是Array，但类型是非Array则抛出此异常
 * @author Lang
 * @see
 */
public class JsonTypeConfusedException extends AbstractSchemaException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 2423142814135044364L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 */
	public JsonTypeConfusedException(final Class<?> clazz, final String attr) {
		super(clazz, -10002, attr);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10002;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
