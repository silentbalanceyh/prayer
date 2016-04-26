package com.prayer.vertx.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.exception.web.UriSpecificationMissingException;
import com.prayer.facade.engine.fun.ErrorHub;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.web.StatusCode;
import com.prayer.vertx.web.model.Envelop;

/**
 * 
 * @author Lang
 *
 */
public final class EnvelopKit {
    // ~ Static Fields =======================================
    /** 错误方法映射表 **/
    private static final ConcurrentMap<StatusCode, ErrorHub> ERR_MAPER = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        /** 404方法 **/
        ERR_MAPER.put(StatusCode.NOT_FOUND, EnvelopKit::build404);
        /** 405方法 **/
        ERR_MAPER.put(StatusCode.METHOD_NOT_ALLOWED, EnvelopKit::build405);
        /** 500方法 **/
        ERR_MAPER.put(StatusCode.INTERNAL_SERVER_ERROR, EnvelopKit::build500);
    }

    // ~ Static Methods ======================================
    /** 根据Http StatusCode构造响应结果 **/
    public static Envelop build(final Class<?> clazz, final StatusCode code, final Object... params) {
        return ERR_MAPER.get(code).build(clazz, params);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static Envelop build500(final Class<?> clazz, final Object... params){
        // 无参数
        final AbstractWebException error = new InternalServerErrorException(clazz);
        return Envelop.failure(error);
    }
    private static Envelop build405(final Class<?> clazz, final Object... params) {
        // 参数0：访问的Http方法
        final AbstractWebException error = new MethodNotAllowedException(clazz, params[0].toString());
        return Envelop.failure(error, StatusCode.METHOD_NOT_ALLOWED);
    }

    private static Envelop build404(final Class<?> clazz, final Object... params) {
        // 参数0：访问的Uri路径
        final AbstractWebException error = new UriSpecificationMissingException(clazz, params[0].toString());
        return Envelop.failure(error, StatusCode.NOT_FOUND);
    }

    private EnvelopKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
