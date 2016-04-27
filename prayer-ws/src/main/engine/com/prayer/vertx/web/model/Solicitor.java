package com.prayer.vertx.web.model;

import java.io.Serializable;

import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 请求对象，请求对象只在Context中传输，不过EventBus，所以不使用ClusterSerializable
 * 
 * @author Lang
 *
 */
@Guarded
public final class Solicitor implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 3782216111357789659L;
    // ~ Instance Fields =====================================
    /** 请求过程中构建遇到的问题 **/
    private transient AbstractException error;
    /** 请求过程中的纯参数信息 **/
    private transient final Buffer params;
    /** 请求过程中的认证头信息 **/
    private transient final Token token;
    /** 真实访问的URI **/
    private transient final String uri;
    /** 需要使用的Headers **/
    private transient final JsonObject headers;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Non-Secure：初始化不安全请求
     * 
     * @param request
     * @param envelop
     * @return
     */
    public static Solicitor create(@NotNull final HttpServerRequest request, @NotNull final Envelop envelop) {
        return new Solicitor(request, envelop, null);
    }

    /**
     * Secure：初始化安全请求
     * 
     * @param request
     * @param envelop
     * @param token
     * @return
     */
    public static Solicitor create(@NotNull final HttpServerRequest request, @NotNull final Envelop envelop,
            final Token token) {
        return new Solicitor(request, envelop, token);
    }

    // ~ Constructors ========================================

    /** 构造函数，带Token **/
    private Solicitor(final HttpServerRequest request, final Envelop envelop, final Token token) {
        /** 1.将请求头的信息填充到headers中 **/
        this.headers = initHeaders(request);
        /** 2.将请求的路径赋值给uri **/
        this.uri = request.path();
        /** 3.初始化Token **/
        this.token = null == token ? Token.create(request) : token;
        
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 当前请求是否合法 **/
    public boolean valid() {
        return null == this.error;
    }

    // ~ Private Methods =====================================

    private JsonObject initHeaders(final HttpServerRequest request) {
        final JsonObject headers = new JsonObject();
        final MultiMap map = request.headers();
        map.forEach(entry -> {
            headers.put(entry.getKey(), entry.getValue());
        });
        return headers;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
