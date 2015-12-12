package com.prayer.handler.web;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Future;
import com.prayer.assistant.WebLogger;
import com.prayer.model.web.Responsor;
import com.prayer.util.cv.Constants;

import io.vertx.core.http.HttpServerResponse;
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
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), String.valueOf(Constants.ORDER.FAILURE),
                context.request().path());
        // 1.包装响应信息
        final HttpServerResponse response = context.response();
        // 2.获取RestfulResult
        final Responsor responser = (Responsor) context.get(Constants.KEY.CTX_RESPONSOR);
        if (null == responser) {
            info(LOGGER, WebLogger.I_COMMON_INFO, " Responser = " + responser);
        } else {
            error(LOGGER, WebLogger.E_COMMON_EXP, responser.getError());
        }
        Future.failure(response, responser.getResult().toString(), responser.getStatus().status(),
                responser.getStatus().name());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
