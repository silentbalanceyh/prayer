package com.prayer.model.bus;

import java.io.Serializable;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractException;
import com.prayer.model.SystemEnum.ResponseCode;
/**
 * 
 * @author Lang
 *
 */
public final class ServiceResult<T> implements Serializable{	// NOPMD
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
	/** 从业务层返回的相应的代码 **/
	private transient ResponseCode responseCode;
	/** 返回的对象信息 **/
	private transient T result;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public ServiceResult(){
		this(null, null);
	}
	/** **/
	public ServiceResult(final T result, final AbstractException error){
		this.setResponse(result, error);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 设置相应信息
	 * @param result
	 * @param error
	 */
	public void setResponse(final T result, final AbstractException error){
		if( null == error ){
			this.setErrorCode(Constants.RC_SUCCESS);
			this.setErrorMessage(null);	// NOPMD
			this.setResponseCode(ResponseCode.SUCCESS);
		}else{
			this.setErrorCode(error.getErrorCode());
			this.setErrorMessage(error.getErrorMessage());
			this.setResponseCode(ResponseCode.FAILURE);
		}
		this.setResult(result);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the responseCode
	 */
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(final ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(final T result) {
		this.result = result;
	}
	// ~ hashCode,equals,toString ============================
}
