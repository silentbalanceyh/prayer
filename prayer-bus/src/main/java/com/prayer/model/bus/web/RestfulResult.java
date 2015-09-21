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
public final class RestfulResult extends ServiceResult<JsonObject>implements Serializable {

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
    /** **/
    public static RestfulResult create(){
        return new RestfulResult();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param result
     * @param statusCode
     * @param error
     */
    public void setResponse(final StatusCode statusCode, final AbstractWebException error) {
        super.setResponse(null, error);
        this.statusCode = statusCode;
        this.error = error;
    }
    /**
     * 拷贝数据
     * @param result
     */
    public void copyFrom(final RestfulResult result){
        this.error = result.getError();
        this.statusCode = result.getStatusCode();
        super.setErrorCode(result.getErrorCode());
        super.setErrorMessage(result.getErrorMessage());
        super.setResponseCode(result.getResponseCode());
        super.setResult(result.getResult());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 默认构造函数 **/
    private RestfulResult(){
        super();
        this.statusCode = StatusCode.OK;
        this.error = null;    // NOPMD
    }
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
