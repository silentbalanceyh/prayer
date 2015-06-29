package com.prayer.exception.system;

import com.prayer.exception.AbstractSystemException;

/**
 * 【Runtime】Error-20001：初始化类型异常
 *
 * @author Lang
 * @see
 */
public class TypeInitException extends AbstractSystemException {

	// ~ Static Fields =======================================
	/**
     *
     */
	private static final long serialVersionUID = -1899652229369453366L;

	// ~ Constructors ========================================

	/**
	 * 
	 * @param clazz
	 * @param memberName
	 * @param input
	 */
	public TypeInitException(final Class<?> clazz, final String memberName,
			final String input) {
		super(clazz, -20001, memberName, input);
	}

	// ~ Override Methods ====================================

	/**
     *
     */
	@Override
	public int getErrorCode() {
		return -20001;
	}
}
