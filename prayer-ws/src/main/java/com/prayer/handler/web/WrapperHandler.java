package com.prayer.handler.web;

import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.assistant.WebLogger;
import com.prayer.constant.Constants;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class WrapperHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperHandler.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 创建方法 **/
    public static WrapperHandler create() {
        return new WrapperHandler();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.WRAPPER);
        // 1.读取请求数据
        final Requestor requestor = Extractor.requestor(context);
        info(LOGGER, " >>>>>>>> Before Wrapper \n" + requestor.getData().encodePrettily());
        final UriModel uri = Extractor.uri(context);
        // 2.参数填充
        if(null == uri){
            // 3.500 Error
            Future.error500(getClass(), context);
        }else{
            // 4.填充参数
            requestor.getParams().put(JsonKey.PARAMS.IDENTIFIER, uri.getGlobalId());
            requestor.getParams().put(JsonKey.PARAMS.SCRIPT, uri.getScript());
            requestor.getParams().put(JsonKey.PARAMS.METHOD, uri.getMethod());
            requestor.getParams().put(JsonKey.PARAMS.FILTERS, uri.getReturnFilters());
            requestor.getParams().put(JsonKey.PARAMS.DATA, requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS));
            // SUCCESS -->
            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
            info(LOGGER, " >>>>>>>> After Wrapper \n" + requestor.getData().encodePrettily());
            context.next();
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
