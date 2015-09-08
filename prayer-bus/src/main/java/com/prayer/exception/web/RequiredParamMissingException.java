package com.prayer.exception.web;

import com.prayer.exception.AbstractWebException;

/**
 * 
 * @author Lang
 *
 */
public class RequiredParamMissingException extends AbstractWebException {

	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1807839960694798981L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param uriPath
	 * @param paramType
	 * @param String
	 */
	public RequiredParamMissingException(final Class<?> clazz, final String uriPath, final String paramType,
			final String paramName) {
		super(clazz, -30001, paramType, paramName, uriPath);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	public int getErrorCode(){
		return -30001;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
