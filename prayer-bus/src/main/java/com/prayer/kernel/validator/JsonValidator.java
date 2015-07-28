package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;

import jodd.json.JsonException;
import jodd.json.JsonParser;

/**
 * Internal Format：Json格式验证器
 * 
 * @author Lang
 *
 */
public class JsonValidator implements Validator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonValidator.class);
	/** **/
	private static final JsonParser PARSER = new JsonParser();

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public boolean validate(Value<?> value) throws AbstractMetadataException {
		boolean ret = false;
		if (null == value) {
			ret = true;
		} else {
			try {
				PARSER.parse(value.literal());
				ret = true;
			} catch (JsonException ex) {
				info(LOGGER, "[E] Json Format Error! Output = " + value.literal(), ex);
				ret = false;
			}

		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
