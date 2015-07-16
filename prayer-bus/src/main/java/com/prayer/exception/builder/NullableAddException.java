package com.prayer.exception.builder;

import com.prayer.exception.AbstractBuilderException;

/**
 * 【Checked】-11002，在有数据的时候，如果添加NOT NULL字段不允许
 * @author Lang
 *
 */
public class NullableAddException extends AbstractBuilderException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -2883182193513474727L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param column
	 * @param table
	 */
	public NullableAddException(final Class<?> clazz, final String column, final String table){
		super(clazz, -11001, column, table);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode(){
		return -11002;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
