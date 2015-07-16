package com.prayer.exception.builder;

import com.prayer.exception.AbstractBuilderException;

/**
 * 【Checked】-11001，将一个字段从NULL改成NOT NULL时因为该字段本身有null值，所以不可更改
 * @author Lang
 *
 */
public class NullableAlterException extends AbstractBuilderException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -8643000244105817309L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param column
	 */
	public NullableAlterException(final Class<?> clazz, final String column){
		super(clazz, -11001, column);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode(){
		return -11001;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
