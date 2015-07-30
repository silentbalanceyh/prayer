package com.prayer.model.type;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;

/**
 * 类型：Xml格式
 *
 * @author Lang
 * @see
 */
public class XmlType extends StringType implements Value<String> {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient Validator innerValidator = singleton("com.prayer.kernel.validator.XmlValidator");

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public XmlType(final String value) throws AbstractMetadataException{
		super(value);
		// Xml内容验证代码
		this.innerValidator.validate(new StringType(value));
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/** **/
	@Override
	public DataType getDataType() {
		return DataType.XML;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "XmlType [value=" + value + "]";
	}
}
