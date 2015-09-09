package com.prayer.vx.validator;

import com.prayer.constant.Constants;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
final class Extractor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** **/
	public static Integer getNumber(final JsonObject config, final String key) {
		Integer integer = Integer.valueOf(Constants.RANGE);
		try {
			// 过滤null值
			if (config.containsKey(key)) {
				integer = config.getInteger(key);
			}
		} catch (ClassCastException ex) {
			integer = null;
		}
		return integer;
	}
	/**
	 * 
	 * @param config
	 * @param key
	 * @return
	 */
	public static String getString(final JsonObject config, final String key){
		String str = Constants.EMPTY_STR;
		try{
			if(config.containsKey(key)){
				str = config.getString(key);
			}
		}catch(ClassCastException ex){
			str = null;
		}
		return str;
	}
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Extractor(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
