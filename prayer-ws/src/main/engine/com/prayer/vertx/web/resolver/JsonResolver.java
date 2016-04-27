package com.prayer.vertx.web.resolver;

import static com.prayer.util.Converter.toStr;

import com.prayer.exception.web._400ParamsExtractingException;
import com.prayer.exception.web._400RequiredParamMissingException;
import com.prayer.exception.web._400WrongRequestFlowException;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.request.Resolver;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.fantasm.vtx.route.AbstractResolver;
import com.prayer.model.meta.vertx.PEUri;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 【Resolver】请求解析器
 * 
 * @author Lang
 *
 */
@Guarded
public class JsonResolver extends AbstractResolver implements Resolver {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject resolve(@NotNull final RoutingContext context, @NotNull final PEUri uri)
            throws AbstractWebException {
        /** 1.读取Method信息 **/
        final HttpMethod method = context.request().method();
        final String path = context.request().path();
        boolean ret = this.isConflict(method, uri.getParamType());
        /** 2.Method/ParamType不匹配，直接抛出以异常 **/
        if (!ret) {
            throw new _400WrongRequestFlowException(getClass(), method.name(), uri.getParamType().name(),
                    this.get(method).encode());
        }
        /** 3.直接处理参数信息，无法读取则抛出异常 **/
        final JsonObject params = this.extract(context, uri);
        if (null == params) {
            throw new _400ParamsExtractingException(getClass(), path, uri.getParamType().name(),
                    method.name());
        }
        /** 4.验证Required参数，丢失Required参数抛出异常 **/
        final String required = this.isRequired(params, uri.getRequiredParam());
        if(null != required){
            throw new _400RequiredParamMissingException(getClass(),required, path, toStr(uri.getRequiredParam()));
        }
        return params;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 子类实现Custom参数提取 **/
    @Override
    public JsonObject extractCustom(final RoutingContext context) throws AbstractWebException {
        return new JsonObject();
    }

    /** 子类实现Body参数提取 **/
    @Override
    public JsonObject extractBody(final RoutingContext context) throws AbstractWebException {
        JsonObject params = null;
        try {
            // 1. --> JsonObject
            final JsonObject obj = context.getBodyAsJson();
            params = new JsonObject();
            params.put(WebKeys.Params.NAME, obj);
            params.put(WebKeys.Params.TYPE, "JObject");
        } catch (DecodeException ex) {
            try {
                // 2. --> JsonArray
                final JsonArray array = context.getBodyAsJsonArray();
                params = new JsonObject();
                params.put(WebKeys.Params.NAME, array);
                params.put(WebKeys.Params.TYPE, "JArray");
            } catch (DecodeException arrEx) {
                params = null;
            }
        }
        // 3. --> String
        if (null == params) {
            try {
                params = new JsonObject();
                final String value = context.getBodyAsString();
                params.put(WebKeys.Params.NAME, value);
                params.put(WebKeys.Params.TYPE, "String");
            } catch (ClassCastException ex) {
                params = null;
            }
        }
        return params;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
