package com.prayer.uca.convertor;

import com.prayer.exception.AbstractWebException;
import com.prayer.kernel.Value;
import com.prayer.model.type.StringType;
import com.prayer.uca.WebConvertor;

import io.vertx.core.json.JsonObject;
/**
 * 
 * @author Lang
 *
 */
public class EncryptionConvertor implements WebConvertor{

	// ~ Static Fields =======================================
	/** 固定的这个转换器的返回值，必须是Engine中定义的Type中的一种。**/
	private final static Class<?> RETURN_TYPE = StringType.class;

	@Override
	public Value<?> convert(String name, Value<?> value, JsonObject config) throws AbstractWebException {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void interrupt(final String name, final JsonObject config) throws AbstractWebException{
		
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
