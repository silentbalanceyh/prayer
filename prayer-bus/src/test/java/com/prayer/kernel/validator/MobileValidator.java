package com.prayer.kernel.validator;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
public class MobileValidator implements Validator{
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(Value<?> value, Object... params) throws AbstractMetadataException {
		boolean ret = false;
		if(DataType.STRING == value.getDataType()){
			if(value.literal().trim().equals("15922611447")){
				ret = true;
			}
		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
