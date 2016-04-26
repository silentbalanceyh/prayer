package com.prayer.security.model;

import static com.prayer.util.debug.Log.jvmError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.model.web.StatusCode;
import com.prayer.util.Encryptor;
import com.prayer.util.string.StringKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 请求用环境变量
 * 
 * @author Lang
 *
 */
@Guarded
public final class BasicRequestor implements Requestor, Serializable, ClusterSerializable {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicRequestor.class);
    /**
     * 
     */
    private static final long serialVersionUID = -7936179017250680612L;
    // ~ Instance Fields =====================================
    /** 请求完整数据 **/
    private transient final JsonObject data;
    /** 请求参数 **/
    @NotNull
    private transient final JsonObject request;
    /** 响应参数 **/
    @NotNull
    private transient JsonObject response;
    /** 传入到Service层的参数 **/
    @NotNull
    private transient JsonObject params;
    /** 认证用 **/
    @NotNull
    private transient JsonObject token;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @return
     */
    public static BasicRequestor create(@NotNull final RoutingContext context) {
        return new BasicRequestor(context);
    }
    // ~ Constructors ========================================

    private BasicRequestor(final RoutingContext context) {
        this.request = new JsonObject();
        this.response = new JsonObject();
        this.params = new JsonObject();
        this.token = new JsonObject();
        this.data = this.initData(context);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void writeToBuffer(final Buffer buffer) {
        this.data.writeToBuffer(buffer);
    }

    /**
     * 
     */
    @Override
    public int readFromBuffer(final int pos, final Buffer buffer) {
        return this.data.readFromBuffer(pos, buffer);
    }

    // ~ Methods =============================================

    /**
     * 
     * @return
     */
    public JsonObject getData() {
        return this.data;
    }

    /**
     * 
     * @return
     */
    public JsonObject getToken() {
        if (null == this.token) {
            this.token = this.data.getJsonObject(JsonKey.TOKEN.NAME);
        }
        return this.token;
    }

    /**
     * 
     * @return
     */
    public JsonObject getResponse() {
        if (null == this.response) {
            this.response = this.data.getJsonObject(JsonKey.RESPONSE.NAME);
        }
        return this.response;
    }

    /**
     * 
     * @return
     */
    public JsonObject getRequest() {
        if (null == this.request) {
            this.response = this.data.getJsonObject(JsonKey.REQUEST.NAME);
        }
        return this.request;
    }

    /**
     * 
     * @return
     */
    public JsonObject getParams() {
        if (null == this.params) {
            this.params = this.data.getJsonObject(JsonKey.PARAMS.NAME);
        }
        return this.params;
    }

    // ~ Private Methods =====================================

    private JsonObject initData(final RoutingContext context) {
        final JsonObject data = new JsonObject();
        // 1.初始化Token
        this.initToken(context);
        // 2.初始化Params
        this.initParams(context);

        data.put(JsonKey.TOKEN.NAME, this.token).put(JsonKey.REQUEST.NAME, this.request)
                .put(JsonKey.PARAMS.NAME, this.params).put(JsonKey.RESPONSE.NAME, this.response);
        return data;
    }

    private void initParams(final RoutingContext context) {
        if (null != context.session()) {
            this.params.put(Constants.PARAM.SESSION, context.session().id());
        }
        this.params.put(Constants.PARAM.METHOD, context.request().method());
        this.params.put(Constants.PARAM.FILTERS, new ArrayList<>());
    }

    private void initToken(final RoutingContext context) {
        // 1. 设置返回Object
        final HttpServerRequest request = context.request();
        final String authorization = request.headers().get(JsonKey.REQUEST.AUTHORIZATION);
        // 2. 填充AUTHORIZATION头信息
        this.request.put(JsonKey.REQUEST.AUTHORIZATION, authorization);
        if (null == authorization) {
            this.response.put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
            this.response.put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.code());
        } else {
            try {
                final String[] parts = authorization.split(String.valueOf(Symbol.SPACE));
                final String schema = parts[0];
                final String[] credentials = new String(Base64.getDecoder().decode(parts[1]))
                        .split(String.valueOf(Symbol.COLON));
                final String username = credentials[0];
                final String password = credentials.length > 1 ? credentials[1] : null; // NOPMD
                if ("Basic".equalsIgnoreCase(schema)) {
                    // 3.Token内容
                    this.token.put(JsonKey.TOKEN.SCHEMA, schema);
                    this.token.put(JsonKey.TOKEN.USERNAME, username);
                    if(StringKit.isNil(password)){
                        this.token.put(JsonKey.TOKEN.PASSWORD, Constants.EMPTY_STR);
                    }else{
                        this.token.put(JsonKey.TOKEN.PASSWORD, Encryptor.encryptMD5(password));
                    }
                    // 4.设置
                    this.response.put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.SUCCESS);
                    this.response.put(JsonKey.RESPONSE.STATUS, StatusCode.OK.code());
                } else {
                    this.response.put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
                    this.response.put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.code());
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                jvmError(LOGGER,ex);
                this.response.put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
                this.response.put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.code());
            } catch (IllegalArgumentException ex) {
                jvmError(LOGGER,ex);
                this.response.put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
                this.response.put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.code());
            }
        }
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return this.data.encode();
    }
}
