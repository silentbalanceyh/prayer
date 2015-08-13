package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;
/**
 * 
 * @author Lang
 *
 */
public class ContentErrorException extends AbstractMetadataException {
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364459141891448172L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public ContentErrorException(final Class<?> clazz, final String format, final String value){
		super(clazz, -11011, format, value);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode(){
		return -11011;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}