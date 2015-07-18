package com.prayer.model.type;

import jodd.json.JsonException;
import jodd.json.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.metadata.Value;

/**
 * 类型: Xml格式的字符串
 *
 * @author Lang
 * @see
 */
public class JsonType extends StringType implements Value<String> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonType.class);
	/** **/
	private static final JsonParser PARSER = new JsonParser();
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public JsonType(final String value){
		super(value);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(final String value) {
		boolean ret = false;
		try {
			PARSER.parse(value);
			ret = true;
		} catch (JsonException ex) {
			if(LOGGER.isErrorEnabled()){
				LOGGER.error("[E] Json format error! Output = " + value,ex);
			}
			ret = false;
		}
		return ret;
	}

	/** **/
	@Override
	public DataType getDataType() {
		return DataType.JSON;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "JsonType [value=" + value + "]";
	}
}
