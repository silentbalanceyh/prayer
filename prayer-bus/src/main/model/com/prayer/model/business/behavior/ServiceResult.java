package com.prayer.model.business.behavior;

import java.io.Serializable;

import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 将ServiceResult变成default的域，仅在特殊的子类中使用
 * @author Lang
 *
 */
class ServiceResult<T> implements Serializable { // NOPMD
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
    private transient AbstractException error; // NOPMD
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
    /** 判断当前结果是否执行成功 **/
    public boolean success() {
        return Constants.RC_SUCCESS == this.errorCode && null == this.error;
    }

    /** 返回SUCCESS **/
    public ServiceResult<T> success(final T result) {
        // Error
        this.error = null;
        this.errorCode = Constants.RC_SUCCESS;
        this.errorMessage = null;
        // Data
        this.result = result;
        return this;
    }

    /** 返回Error **/
    public ServiceResult<T> error(final AbstractException error) {
        this.setError(error);
        return this;
    }

    /** 返回Failure **/
    public ServiceResult<T> failure(final AbstractException failure) {
        this.setError(failure);
        return this;
    }

    // ~ Private Methods =====================================
    private void setError(final AbstractException error) {
        this.error = error;
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
        this.result = null; // NOPMD
    }

    // ~ Get/Set =============================================
    /**
     * 
     * @return
     */
    public AbstractException getError() {
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
     * @return the result
     */
    public T getResult() {
        return result;
    }
    // ~ hashCode,equals,toString ============================
}
