package com.prayer.exception.unchecked;

import com.prayer.exception.AbstractSystemException;
/**
 * 【Runtime】初始化类型异常
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
	 * @param clazz
	 * @param memberName
	 */
	public TypeInitException(final Class<?> clazz, final String memberName, final String input) {
		super("[E] Class -> " + clazz.getName() + ",Member -> " + memberName
				+ " could not be initialized! \nInput = " + input);
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
