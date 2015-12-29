package com.prayer.handler.route;

import com.prayer.assistant.Future;
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
        // 1.包装响应信息
        final HttpServerResponse response = context.response();
        // 2.获取RestfulResult
        final Responsor responser = (Responsor) context.get(Constants.KEY.CTX_RESPONSOR);

        Future.failure(response, responser.getResult().toString(), responser.getStatus().status(),
                responser.getStatus().name());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
