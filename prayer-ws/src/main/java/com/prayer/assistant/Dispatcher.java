package com.prayer.assistant;

import static com.prayer.assistant.WebLogger.error;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.util.StringKit;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public final class Dispatcher { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 新版的Dispatcher功能
     * 
     * @param clazz
     * @param result
     * @param context
     * @return
     */
    public static boolean requestDispatch(final Class<?> clazz,
            final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result, final RoutingContext context) {
        final HttpServerRequest request = context.request();
        
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final ConcurrentMap<HttpMethod, UriModel> uriMap = result.getResult();
            if (uriMap.isEmpty()) {
                // 404 Resources Not Found
                Future.error404(clazz, context, request.path());
                return false;
            } else {
                final UriModel uriSpec = uriMap.get(request.method());
                if (null == uriSpec) {
                    // 405 Method Not Allowed
                    Future.error405(clazz, context, request.method());
                    return false;
                } else {
                    final String params = getErrorParam(uriSpec, context);
                    if (StringKit.isNonNil(params)) {
                        if ("DECODE".equals(params)) {
                            // 401需要Skip这种情况
                            Future.error400(clazz, context, -30010, request.path());
                            return false;
                        } else {
                            Future.error400(clazz, context, -30001, request.path(), uriSpec.getParamType().toString(),
                                    params);
                            return false;
                        }
                    } else {
                        return true;
                    }
                }
            }
        } else {
            // 500 Internal Error
            Future.error500(clazz, context);
            return false;
        }
    }
/*
    *//**
     * 
     * @param result
     * @param webRef
     * @param context
     * @param clazz
     * @return
     *//*
    public static AbstractWebException requestDispatch(final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result,
            final RestfulResult webRef, final RoutingContext context, final Class<?> clazz) {
        AbstractWebException error = null;
        final HttpServerRequest request = context.request();
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final ConcurrentMap<HttpMethod, UriModel> uriMap = result.getResult();
            if (uriMap.isEmpty()) {
                // 404 Resources Not Found
                error = HttpErrHandler.error404(webRef, clazz, request.path());
            } else {
                final UriModel uriSpec = uriMap.get(request.method());
                if (null == uriSpec) {
                    // 405 Method Not Allowed
                    error = HttpErrHandler.error405(webRef, clazz, request.method());
                } else {
                    final String errParam = getErrorParam(uriSpec, context);
                    if (StringKit.isNonNil(errParam)) {
                        if ("DECODE".equals(errParam)) {
                            // Authenticate需要Skip这种情况
                            error = HttpErrHandler.error400E30010(webRef, clazz, request.path());
                        } else {
                            error = HttpErrHandler.error400E30001(webRef, clazz, request.path(),
                                    uriSpec.getParamType().toString(), errParam);
                        }
                    }
                }
            }
        } else {
            // 500 Internal Server
            error = HttpErrHandler.error500(webRef, clazz);
        }
        return error;
    }*/

    // 参数规范的处理流程
    public static String getErrorParam(final UriModel uri, final RoutingContext context) { // NOPMD
        String retParam = null;
        final List<String> paramList = uri.getRequiredParam();
        final HttpServerRequest request = context.request();
        if (ParamType.QUERY == uri.getParamType()) {
            // 从Query String中获取参数
            final MultiMap params = request.params();
            for (final String param : paramList) {
                if (StringKit.isNil(params.get(param))) {
                    retParam = param;
                    break;
                }
            }
        } else if (ParamType.FORM == uri.getParamType()) {
            // 从Form中获取参数
            final MultiMap params = request.formAttributes();
            for (final String param : paramList) {
                if (StringKit.isNil(params.get(param))) {
                    retParam = param;
                    break;
                }
            }
        } else {
            // 从Body中直接获取参数
            final String body = context.getBodyAsString();
            if (StringKit.isNonNil(body)) {
                try {
                    final JsonObject params = context.getBodyAsJson();
                    for (final String param : paramList) {
                        if (StringKit.isNil(params.getString(param))) {
                            retParam = param;
                            break;
                        }
                    }
                } catch (DecodeException ex) {
                    retParam = "DECODE";
                    error(LOGGER, WebLogger.E_COMMON_EXP, ex.toString());
                }
            }
        }
        return retParam;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Dispatcher() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
