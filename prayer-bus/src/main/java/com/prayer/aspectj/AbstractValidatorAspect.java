package com.prayer.aspectj;

import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.type.DataType;
import com.prayer.util.Instance;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractValidatorAspect {
	// ~ Static Fields =======================================
	/** 符合该验证器的属性 **/
	protected static final DataType[] T_PATTERNS = new DataType[] { DataType.STRING, DataType.XML, DataType.JSON,
			DataType.SCRIPT };
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	protected FieldModel getField(final Object instance, final String field){
		final GenericSchema schema = Instance.field(instance,"_schema");
		FieldModel fieldSchema = null;
		if(null != schema && null != field){
			fieldSchema = schema.getFields().get(field);
		}
		return fieldSchema;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
