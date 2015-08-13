package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;
/**
 * 
 * @author Lang
 *
 */
public class InvalidPKParameterException extends AbstractMetadataException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -2040080955697695940L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param param
	 * @param value
	 * @param table
	 */
	public InvalidPKParameterException(final Class<?> clazz, final String param, final String table){
		super(clazz, -11008, param, table);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode(){
		return -11008;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}