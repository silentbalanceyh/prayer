package com.prayer.handler.web;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import com.prayer.assistant.WebLogger;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.model.bus.web.RestfulResult;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class FailureHandler implements ErrorHandler {

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(FailureHandler.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 创建方法 **/
    public static FailureHandler create() {
        return new FailureHandler();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 直接从Context中获取处理结果
     */
    @Override
    public void handle(@NotNull final RoutingContext context) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.FAILURE);
        // 1.包装响应信息
        final HttpServerResponse response = context.response();
        // 2.获取RestfulResult
        final RestfulResult webRet = (RestfulResult) context.get(Constants.KEY.CTX_ERROR);
        info(LOGGER, WebLogger.I_REST_RESULT, webRet);
        error(LOGGER, WebLogger.E_COMMON_EXP, webRet.getError());
        // 3.包装Error信息生成统一的Error格式
        final JsonObject retData = webRet.getResult();
        // 3.1.401的特殊信息设置
        if (null != context.get(Constants.RET.AUTH_ERROR)) {
            retData.put(Constants.RET.AUTH_ERROR, context.get(Constants.RET.AUTH_ERROR).toString());
        }
        // 4.获取响应的信息
        final String content = retData.encodePrettily();

        // TODO: 5.后期需要改动，测试因为使用浏览器，暂时使用这种
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + Resources.SYS_ENCODING);
        response.putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.getBytes().length));
        // 6.设置StatusCode和Error
        response.setStatusCode(retData.getInteger(Constants.RET.STATUS_CODE));
        response.setStatusMessage(retData.getString(Constants.RET.ERROR));
        response.write(content, Resources.SYS_ENCODING.name());
        response.end();
        response.close();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
