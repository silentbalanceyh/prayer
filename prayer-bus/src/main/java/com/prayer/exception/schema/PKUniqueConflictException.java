package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10025：非COLLECTION的Policy主键的unique必须true
 * @author Lang
 *
 */
public class PKUniqueConflictException extends AbstractSchemaException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 1785940315089868228L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 */
	public PKUniqueConflictException(final Class<?> clazz, final String attr){
		super(clazz, -10025, attr);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode(){
		return -10025;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
