package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;

/**
 * 
 * @author Lang
 *
 */
public class MoreThanOneException extends AbstractMetadataException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -6275237200199786236L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param table
	 */
	public MoreThanOneException(final Class<?> clazz, final String table){
		super(clazz, -11006, table);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @return
	 */
	@Override
	public int getErrorCode(){
		return -11006;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}