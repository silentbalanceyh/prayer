package com.prayer.handler.web; // NOPMD

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.singleton;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Dispatcher;
import com.prayer.assistant.Extractor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.SystemEnum.ParamType;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 【Step 1】参数的基本检查，访问接口的第一步
 * 
 * @author Lang
 *
 */
@Guarded
public class RouterHandler implements Handler<RoutingContext> { // NOPMD

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterHandler.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 创建方法 **/
    public static RouterHandler create() {
        return new RouterHandler();
    }

    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RouterHandler() {
        this.service = singleton(ConfigSevImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /**
     * 
     */
    @Override
    public void handle(@NotNull final RoutingContext context) {
        final String path = Extractor.path(context);
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), String.valueOf(Constants.ORDER.ROUTER),path);
        // 1.获取请求Request和相应Response引用
        final Requestor requestor = Extractor.requestor(context);
        
        final HttpServerRequest request = context.request();
        // 2.从系统中按URI读取接口规范
        final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service.findUri(path);
        // 3.请求转发，去除掉Error过后的信息
        if (Dispatcher.requestDispatch(getClass(), result, context)) {
            // SUCCESS -->
            // 4.保存UriModel到Request节点中
            final UriModel uri = result.getResult().get(request.method());
            // 5.序列化URI模型
            context.put(Constants.KEY.CTX_URI, uri);
            // 6.获取请求参数
            final JsonObject params = this.extractParams(context, uri);
            requestor.getRequest().put(JsonKey.REQUEST.PARAMS, params);
            // 7.填充Requestor
            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
            context.next();
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonObject extractParams(final RoutingContext context, final UriModel uri) {
        JsonObject retJson = new JsonObject();
        final HttpServerRequest request = context.request();
        if (ParamType.QUERY == uri.getParamType()) {
            retJson.clear();
            final MultiMap params = request.params();
            final Iterator<Map.Entry<String, String>> kvPair = params.iterator();
            while (kvPair.hasNext()) {
                final Map.Entry<String, String> entity = kvPair.next();
                retJson.put(decodeURL(entity.getKey()), decodeURL(entity.getValue()));
            }
        } else if (ParamType.FORM == uri.getParamType()) {
            retJson.clear();
            final MultiMap params = request.formAttributes();
            final Iterator<Map.Entry<String, String>> kvPair = params.iterator();
            while (kvPair.hasNext()) {
                final Map.Entry<String, String> entity = kvPair.next();
                retJson.put(decodeURL(entity.getKey()), decodeURL(entity.getValue()));
            }
        } else {
            final String body = context.getBodyAsString();
            if (StringKit.isNonNil(body)) {
                retJson = new JsonObject(decodeURL(context.getBodyAsJson().encode()));
            }
        }
        info(LOGGER, "Param Data : " + retJson.encode());
        return retJson;
    }

    private String decodeURL(final String inputValue) {
        String ret = inputValue;
        try {
            ret = URLDecoder.decode(inputValue, Resources.SYS_ENCODING.name());
        } catch (UnsupportedEncodingException ex) {
            debug(LOGGER, "JVM.ENCODING", ex, Resources.SYS_ENCODING.name());
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}