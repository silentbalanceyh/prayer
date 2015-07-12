package com.prayer.exception.system;

import com.prayer.exception.AbstractSystemException;

/**
 * 【Checked】Error-20005：Loading Schema异常
 * @author Lang
 *
 */
public class DataLoadingException extends AbstractSystemException{
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -4340358874356913965L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 */
	public DataLoadingException(final Class<?> clazz, final String process){
		super(clazz,-20005, process);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode(){
		return -20005;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
