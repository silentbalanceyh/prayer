package com.prayer.meta.data.type;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.meta.DataType;
import com.prayer.meta.Value;

/**
 * 类型：Script格式【默认JavaScript】
 *
 * @author Lang
 * @see
 */
public class ScriptType extends StringType implements Value<String>{
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptType.class);
	/** **/
	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("javascript");
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public ScriptType(final String value){
		super(value);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(final String value){
		boolean ret = false;
		try{
			ENGINE.eval(value);
			ret = true;
		}catch(ScriptException ex){
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
		return DataType.SCRIPT;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "ScriptType [value=" + value + "]";
	}
}
