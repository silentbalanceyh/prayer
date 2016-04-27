package com.prayer.fantasm.vtx.route;

import java.util.List;

import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.util.string.StringKit;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractResolver {
    // ~ Static Fields =======================================
    /** 方法和参数类型规范 **/
    private static final JsonObject METHOD_SPEC = new JsonObject();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 填充方法和参数规范 **/
    static {
        /** GET **/
        final JsonArray types = new JsonArray();
        types.add(ParamType.CUSTOM);
        types.add(ParamType.FORM);
        types.add(ParamType.QUERY);
        METHOD_SPEC.put(HttpMethod.GET.name(), types.copy());
        /** DELETE **/
        types.add(ParamType.BODY);
        METHOD_SPEC.put(HttpMethod.DELETE.name(), types.copy());
        /** POST/PUT **/
        types.remove(ParamType.QUERY);
        METHOD_SPEC.put(HttpMethod.PUT.name(), types.copy());
        METHOD_SPEC.put(HttpMethod.POST.name(), types.copy());
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    protected abstract JsonObject extractCustom(RoutingContext context) throws AbstractWebException;
    /** **/
    protected abstract JsonObject extractBody(RoutingContext context) throws AbstractWebException;
    // ~ Override Methods ====================================
    /**
     * 方法是否冲突
     * 
     * @param method
     * @param type
     * @return
     */
    protected boolean isConflict(final HttpMethod method, final ParamType type) {
        final JsonArray types = METHOD_SPEC.getJsonArray(method.name());
        boolean ret = true;
        if (types.contains(type)) {
            ret = false;
        }
        return ret;
    }
    /**
     * 读取所有期望值
     * @param method
     * @return
     */
    protected JsonArray get(final HttpMethod method){
        return METHOD_SPEC.getJsonArray(method.name());
    }
    /**
     * 
     * @param params
     * @param requiredParams
     * @return
     */
    protected String isRequired(final JsonObject params, final List<String> requiredParams){
        String required = null;
        for(final String field: requiredParams){
            if(!params.containsKey(field)){
                required = field;
                break;
            }
        }
        return required;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    protected JsonObject extract(final RoutingContext context, final PEUri uri) throws AbstractWebException {
        JsonObject data = null;
        final HttpServerRequest request = context.request();
        if (ParamType.QUERY == uri.getParamType()) {
            data = this.extractQuery(request);
        } else if (ParamType.FORM == uri.getParamType()) {
            data = this.extractForm(request);
        } else if (ParamType.BODY == uri.getParamType()) {
            data = this.extractBody(context);
        } else {
            // CUSTOM类型
            data = this.extractCustom(context);
        }
        return data;
    }
    private JsonObject extract(final MultiMap paramMap) {
        final JsonObject params = new JsonObject();
        paramMap.forEach(item -> {
            if (StringKit.isNonNil(item.getKey()) && StringKit.isNonNil(item.getValue())) {
                params.put(item.getKey(), item.getValue());
            }
        });
        return params;
    }
    

    private JsonObject extractForm(final HttpServerRequest request) {
        final MultiMap parameters = request.formAttributes();
        return this.extract(parameters);
    }

    private JsonObject extractQuery(final HttpServerRequest request) {
        final MultiMap parameters = request.params();
        return this.extract(parameters);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
