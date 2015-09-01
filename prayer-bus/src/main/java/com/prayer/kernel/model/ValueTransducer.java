package com.prayer.kernel.model;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DataType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;

/**
 * 
 * @author Lang
 *
 */
final class ValueTransducer implements Transducer {	// NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Value<?> getValue(final DataType type, final String value) throws AbstractMetadataException{	// NOPMD
		Value<?> ret = null;
		switch (type) {
		case BOOLEAN:
			ret = new BooleanType(value);
			break;
		case INT:
			ret = new IntType(value);
			break;
		case LONG:
			ret = new LongType(value);
			break;
		case DECIMAL:
			ret = new DecimalType(value);
			break;
		case DATE:
			ret = new DateType(value);
			break;
		case BINARY:
			ret = new BinaryType(value);
			break;
		case XML:
			ret = new XmlType(value);
			break;
		case SCRIPT:
			ret = new ScriptType(value);
			break;
		case JSON:
			ret = new JsonType(value);
			break;
		default:
			ret = new StringType(value);
			break;
		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
