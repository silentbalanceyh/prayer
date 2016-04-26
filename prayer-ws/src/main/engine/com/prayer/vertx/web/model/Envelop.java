package com.prayer.vertx.web.model;

import java.io.Serializable;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.model.web.StatusCode;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Envelop implements Serializable, ClusterSerializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -9108149074184872121L;
    // ~ Instance Fields =====================================
    /** 返回的状态代码 **/
    private transient StatusCode status;
    /** 是否包含自定义错误 **/
    private transient AbstractException error;
    /** 最终返回数据信息 **/
    private transient final Buffer data;
    /** Http Header信息 **/
    private transient final JsonObject headers;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** Failure **/
    public static Envelop failure(final AbstractException error) {
        return new Envelop(error, StatusCode.INTERNAL_SERVER_ERROR);
    }

    /** Failure **/
    public static Envelop failure(final AbstractException error, final StatusCode status) {
        return new Envelop(error, status);
    }

    /** Success **/
    public static Envelop success(final ActResponse response) {
        return new Envelop(response, StatusCode.OK);
    }

    /** Success **/
    public static Envelop success(final ActResponse response, final StatusCode status) {
        return new Envelop(response, status);
    }
    // ~ Constructors ========================================

    // ~ Failure Response ====================================

    private Envelop(final AbstractException error, final StatusCode status) {
        this.headers = new JsonObject();
        this.data = Buffer.buffer();
        this.error = error;
        this.status = status;
    }

    // ~ Success Response ====================================
    /** 根据Act的Response生成对应的响应 **/
    private Envelop(final ActResponse response, final StatusCode status) {
        this.headers = new JsonObject();
        if (response.success()) {
            this.data = Buffer.buffer(response.getResult().encode());
            this.error = null;
        } else {
            this.data = Buffer.buffer();
            this.error = response.getError();
        }
        this.status = status;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void writeToBuffer(final Buffer buffer) {
        // 响应结果写入到Buffer中
        this.result().writeToBuffer(buffer);
    }

    /**
     * 
     */
    @Override
    public int readFromBuffer(final int pos, final Buffer buffer) {
        // 响应结果读取到Buffer中
        return this.result().readFromBuffer(pos, buffer);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.result().toString();
    }

    // ~ Methods =============================================
    /** 添加Header **/
    public void addHeader(final MultiMap headers) {
        headers.forEach(item -> {
            this.headers.put(item.getKey(), item.getValue());
        });
    }

    /** 添加Header **/
    public void addHeader(final String key, final String value) {
        this.headers.put(key, value);
    }

    /** 重设状态代码 **/
    public void setStatus(final StatusCode status) {
        this.status = status;
    }
    /** **/
    public boolean succeeded(){
        return null == this.error;
    }
    /** 读取最终响应结果 **/
    public JsonObject result() {
        final JsonObject result = new JsonObject();
        /** 1.构建Status节点 **/
        if (null != status) {
            final JsonObject status = new JsonObject();
            status.put(WebKeys.Envelope.Status.CODE, this.status.status());
            status.put(WebKeys.Envelope.Status.TITLE, this.status.toString());
            result.put(WebKeys.Envelope.STATUS, status);
        }
        /** 2.构建Error节点 **/
        if (null == this.error) {
            /** 3.无Error则正常返回 **/
            final JsonObject data = new JsonObject();
            /** 4.放入Header **/
            if (null != this.headers) {
                data.put(WebKeys.Envelope.Data.HEADER, this.headers);
            }
            /** 5.放入Body **/
            if (null != this.data && Constants.ZERO < this.data.length()) {
                /** 5.读取Data信息 **/
                final JsonObject content = new JsonObject();
                content.readFromBuffer(Constants.POS, this.data);
                /** 6.生成响应 **/
                data.put(WebKeys.Envelope.Data.BODY, content);
            }
            result.put(WebKeys.Envelope.DATA, data);
        } else {
            /** 3.有Error按照Error返回 **/
            final JsonObject error = new JsonObject();
            error.put(WebKeys.Envelope.Error.CODE, this.error.getErrorCode());
            error.put(WebKeys.Envelope.Error.MESSAGE, this.error.getErrorMessage());
            result.put(WebKeys.Envelope.ERROR, error);
        }
        return result;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}