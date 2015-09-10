package com.prayer.model.bus.web;

import java.io.Serializable;

import com.prayer.exception.AbstractWebException;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * 继承关系，从ServiceResult<T>继承过来
 * 
 * @author Lang
 *
 */
public class RestfulResult extends ServiceResult<JsonObject>implements Serializable {

	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 6434208462744921973L;
	// ~ Instance Fields =====================================
	/** HTTP状态代码 **/
	private transient StatusCode statusCode;
	/** Exception **/
	private transient AbstractWebException error;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public RestfulResult(final StatusCode statusCode) {
		this(null, statusCode, null);
	}

	/** **/
	public RestfulResult(final JsonObject result, final StatusCode statusCode, final AbstractWebException error) { // NOPMD
		this.setResponse(result, statusCode, error);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @param result
	 * @param statusCode
	 * @param error
	 */
	public void setResponse(final JsonObject result, final StatusCode statusCode, final AbstractWebException error) {
		this.setResponse(result, error);
		this.statusCode = statusCode;
		this.error = error;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * 
	 * @return
	 */
	public AbstractWebException getError(){
		return this.error;
	}
	/**
	 * @return the statusCode
	 */
	public StatusCode getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	// ~ hashCode,equals,toString ============================

	/** **/
	@Override
	public String toString() {
		return "RestfulResult [statusCode=" + statusCode + ", error=" + error.getErrorMessage() + "]";
	}
}
