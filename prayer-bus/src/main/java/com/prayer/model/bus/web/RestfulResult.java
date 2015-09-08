package com.prayer.model.bus.web;

import java.io.Serializable;

import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * 继承关系，从ServiceResult<T>继承过来
 * @author Lang
 *
 */
public class RestfulResult extends ServiceResult<JsonObject> implements Serializable{

	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 6434208462744921973L;
	// ~ Instance Fields =====================================
	/** HTTP状态代码 **/
	private transient StatusCode statusCode;
	
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	
	// ~ hashCode,equals,toString ============================
}
