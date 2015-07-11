package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10022：keys中的PK定义multi必须匹配__meta__中的PK Policy，否则冲突
 * 
 * @author Lang
 *
 */
public class MultiForPKPolicyException extends AbstractSchemaException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 3368737553974375913L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param policy
	 * @param isMulti
	 */
	public MultiForPKPolicyException(final Class<?> clazz, final String policy, final String isMulti) {
		super(clazz, -10022, policy, isMulti);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10022;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
