package com.prayer.exception.metadata;

import com.prayer.exception.AbstractMetadataException;
/**
 * 
 * @author Lang
 *
 */
public class PKValueMissingException extends AbstractMetadataException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 2638975545160960973L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param colName
	 * @param table
	 */
	public PKValueMissingException(final Class<?> clazz, final String colName, final String table){
		super(clazz, -11007, colName, table);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public int getErrorCode(){
		return -11007;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}