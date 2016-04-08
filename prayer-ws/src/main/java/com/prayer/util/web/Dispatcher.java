package com.prayer.util.web;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.string.StringKit.decodeURL;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.constant.Constants;
import com.prayer.model.business.ServiceResult;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.util.string.StringKit;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public final class Dispatcher { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);
    /** **/
    private static final String DECODE = "DECODE";

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
            final ServiceResult<ConcurrentMap<HttpMethod, PEUri>> result, final RoutingContext context) {
        final HttpServerRequest request = context.request();
        // 1.内部500Error
        if (ResponseCode.SUCCESS != result.getResponseCode()) {
            // 500 Internal Error
            Future.error500(clazz, context);
            return false; // NOPMD
        }
        // 2.URI的获取
        final ConcurrentMap<HttpMethod, PEUri> uriMap = result.getResult();
        if (uriMap.isEmpty()) {
            // 404 Resources Not Found
            Future.error404(clazz, context, request.path());
            return false; // NOPMD
        }
        // 3.405 Method Not Allowed
        final PEUri uriSpec = uriMap.get(request.method());
        if (null == uriSpec) {
            // 405 Method Not Allowed
            Future.error405(clazz, context, request.method());
            return false; // NOPMD
        }
        // 4.特殊的参数信息
        final String param = getErrorParam(uriSpec, context);
        if (StringKit.isNonNil(param)) {
            if (DECODE.equals(param)) {
                // 401需要Skip这种情况
                Future.error400(clazz, context, -30010, request.path());
            } else {
                Future.error400(clazz, context, -30001, request.path(), uriSpec.getParamType().toString(), param);
            }
            return false; // NOPMD
        }
        return true;
    }

    /**
     * 参数规范的处理流程
     * 
     * @param uri
     * @param context
     * @return
     */
    public static String getErrorParam(final PEUri uri, final RoutingContext context) { // NOPMD
        String retParam = null;
        final List<String> paramList = uri.getRequiredParam();
        final HttpServerRequest request = context.request();
        if (ParamType.CUSTOM != uri.getParamType()) {
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
                        final JsonObject params = new JsonObject(decodeURL(body));
                        for (final String param : paramList) {
                            if (params.containsKey(param)) {
                                if (StringUtil.equals(param, Constants.PARAM.PAGE.NAME)) {
                                    // Special for page parameters
                                    final JsonObject page = params.getJsonObject(Constants.PARAM.PAGE.NAME);
                                    if (null == page) {
                                        retParam = param;
                                        break;
                                    } else {
                                        if (!page.containsKey(Constants.PARAM.PAGE.PAGE_INDEX)) { // NOPMD
                                            retParam = param + "->" + Constants.PARAM.PAGE.PAGE_INDEX;
                                            break;
                                        } else if (!page.containsKey(Constants.PARAM.PAGE.PAGE_SIZE)) {
                                            retParam = param + "->" + Constants.PARAM.PAGE.PAGE_SIZE;
                                            break;
                                        }
                                    }
                                } else if (StringUtil.equals(param, Constants.PARAM.ORDERS)) {
                                    // Speical for orders parameters
                                    final JsonArray orders = params.getJsonArray(Constants.PARAM.ORDERS);
                                    if (null == orders) {
                                        retParam = param;
                                        break;
                                    }
                                } else {
                                    if (StringKit.isNil(params.getString(param))) {
                                        retParam = param;
                                        break;
                                    }
                                }
                            } else {
                                retParam = param;
                                break;
                            }
                        }
                    } catch (DecodeException ex) {
                        retParam = DECODE;
                        jvmError(LOGGER, ex);
                    } catch (ClassCastException ex) {
                        retParam = DECODE;
                        jvmError(LOGGER, ex);
                    }
                } else {
                    // Body 不填充任何值的时候
                    retParam = DECODE;
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
