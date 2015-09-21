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
import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.exception.AbstractException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.UriModel;

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
    public void handle(@NotNull final RoutingContext routingContext) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.ROUTER);
        // 1.获取请求Request和相应Response引用
        final HttpServerRequest request = routingContext.request();

        // 2.从系统中按URI读取接口规范
        final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service.findUri(request.path());
        final RestfulResult webRet = RestfulResult.create();

        // 3.请求转发，去除掉Error过后的信息
        final AbstractException error = Dispatcher.requestDispatch(result, webRet, routingContext,getClass());

        // 4.根据Error设置
        if (null == error) {
            // SUCCESS -->
            // 5.1.保存UriModel到Context中
            final UriModel uri = result.getResult().get(request.method());
            routingContext.put(Constants.KEY.CTX_URI, uri);
            // 6.1.设置参数到Context中提供给下一个Handler处理
            final JsonObject params = extractParams(routingContext, uri);
            routingContext.put(Constants.KEY.CTX_PARAMS, params);
            routingContext.next();
        } else {
            // 5.2.保存RestfulResult到Context中
            routingContext.put(Constants.KEY.CTX_ERROR, webRet);
            routingContext.fail(webRet.getStatusCode().status());
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
            retJson = new JsonObject(decodeURL(context.getBodyAsJson().encodePrettily()));
        }
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