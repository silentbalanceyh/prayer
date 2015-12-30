package com.prayer.handler.route;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Log.debug;
import static com.prayer.util.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.model.JsonKey;
import com.prayer.model.Requestor;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.log.DebugKey;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServiceHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 创建方法 **/
    public static ServiceHandler create() {
        return new ServiceHandler();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_STG_HANDLER, getClass().getName(), String.valueOf(Constants.ORDER.ENG.SERVICE),
                context.request().path());

        // 1.从Context中提取参数信息
        final Requestor requestor = Extractor.requestor(context);
        final UriModel uri = Extractor.uri(context);
        // 2.Service获取参数信息
        if (null == uri) {
            // 500 Internal Server
            Future.error500(getClass(), context);
        } else {
            // Param参数准备过程
            requestor.getParams().put(JsonKey.PARAMS.IDENTIFIER, uri.getGlobalId());
            requestor.getParams().put(JsonKey.PARAMS.SCRIPT, uri.getScript());
            requestor.getParams().put(JsonKey.PARAMS.METHOD, uri.getMethod());
            requestor.getParams().put(JsonKey.PARAMS.FILTERS, uri.getReturnFilters());
            requestor.getParams().put(JsonKey.PARAMS.DATA,
                    requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS));
            // 设置VertX信息
            final Vertx vertx = context.vertx();
            final EventBus bus = vertx.eventBus();
            // 设置Class类信息
            try {
                // 发送Message到Event Bus
                final Handler<AsyncResult<Message<Object>>> sender = instance(uri.getSender(), context.response());
                // 检查Sender的合法性
                Interruptor.interruptClass(getClass(), uri.getSender(), "Sender");
                Interruptor.interruptImplements(getClass(), uri.getSender(), Handler.class);
                // 通过Sender发送Message
                bus.send(uri.getAddress(), requestor.getParams(), sender);
            } catch (AbstractWebException ex) {
                peError(LOGGER, ex);
                Future.error500(getClass(), context);
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
