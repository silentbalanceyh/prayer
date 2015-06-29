package com.prayer.exception.system;

import com.prayer.exception.AbstractSystemException;

/**
 * 【Runtime】Error-20003：Json解析异常
 * 
 * @author Lang
 * @see
 */
public class JsonParserException extends AbstractSystemException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 5287339280577064202L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param jsonContent
	 */
	public JsonParserException(final Class<?> clazz, final String jsonContent) {
		super(clazz, -20003, jsonContent);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -20003;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
