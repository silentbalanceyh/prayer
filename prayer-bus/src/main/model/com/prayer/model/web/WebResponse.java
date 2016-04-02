package com.prayer.model.web;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class WebResponse extends ServiceResult<JsonObject> {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -4764713748737525998L;
    // ~ Instance Fields =====================================
    /**
     * HTTP状态代码以及描述信息
     */
    private transient StatusCode code;
    /**
     * HTTP方法阐述
     */
    private transient HttpMethod method;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public WebResponse() {
        super();
        this.code = StatusCode.OK;
        this.method = HttpMethod.GET;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public WebResponse success(final JsonObject result) {
        super.success(result);
        this.code = StatusCode.OK;
        return this;
    }

    /** **/
    @Override
    public WebResponse failure(final AbstractException failure) {
        return this.failure(failure, this.code);
    }

    // ~ Methods =============================================
    /** **/
    public WebResponse failure(final AbstractException failure, final StatusCode code) {
        super.failure(failure);
        this.code = code;
        return this;
    }

    /**
     * 
     * @return
     */
    public StatusCode getStatusCode() {
        return this.code;
    }

    /**
     * 
     */
    public HttpMethod getMethod() {
        return this.method;
    }

    /**
     * 
     * @param method
     */
    public void setMethod(final HttpMethod method) {
        this.method = method;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
