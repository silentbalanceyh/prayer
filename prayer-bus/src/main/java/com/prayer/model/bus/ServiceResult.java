package com.prayer.model.bus;

import java.io.Serializable;

import com.prayer.base.exception.AbstractException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;

/**
 * 
 * @author Lang
 *
 */
public class ServiceResult<T> implements Serializable { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 3777349638494092940L;
	// ~ Instance Fields =====================================
	/** 如果是有自定义异常，则为自定义异常代码 **/
	private transient int errorCode;
	/** 如果有自定义异常，则为自定义异常信息 **/
	private transient String errorMessage;
	/** 内部Error **/
	private transient AbstractException error;     // NOPMD
	/** 从业务层返回的相应的代码 **/
	private transient ResponseCode responseCode;
	/** 返回的对象信息 **/
	private transient T result;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public ServiceResult() {
		// DEFAULT
		this.success(null);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** 返回SUCCESS **/
	public ServiceResult<T> success(final T result) {
		// Error
		// this.error = null;
		this.errorCode = Constants.RC_SUCCESS;
		// this.errorMessage; 
		// Data
		this.result = result;
		// Flat
		this.responseCode = ResponseCode.SUCCESS;
		return this;
	}

	/** 返回Error **/
	public ServiceResult<T> error(final AbstractException error) {
		this.setError(ResponseCode.ERROR, error);
		return this;
	}

	/** 返回Failure **/
	public ServiceResult<T> failure(final AbstractException failure) {
		this.setError(ResponseCode.FAILURE, failure);
		return this;
	}
	// ~ Private Methods =====================================
	private void setError(final ResponseCode responseCode, final AbstractException error) {
		this.error = error;
		this.errorCode = error.getErrorCode();
		this.errorMessage = error.getErrorMessage();
		this.result = null;   // NOPMD
		this.responseCode = responseCode;
	}

	// ~ Get/Set =============================================
	/**
	 * 
	 * @return
	 */
	public AbstractException getServiceError() {
		return this.error;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the responseCode
	 */
	public ResponseCode getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}
	// ~ hashCode,equals,toString ============================
}
