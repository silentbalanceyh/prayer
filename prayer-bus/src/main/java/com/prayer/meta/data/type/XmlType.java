package com.prayer.meta.data.type;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.meta.DataType;
import com.prayer.meta.Value;

/**
 * 类型：Xml格式
 *
 * @author Lang
 * @see
 */
public class XmlType extends StringType implements Value<String>{
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlType.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public XmlType(final String value){
		super(value);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(final String value){
		boolean ret = false;
		try{
			DocumentHelper.parseText(value);
			ret = true;
		}catch(DocumentException ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[E] Script error! Input = " + value, ex);
			}
			ret = false;
		}
		return ret;
	}
	/** **/
	@Override
	public DataType getDataType(){
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
