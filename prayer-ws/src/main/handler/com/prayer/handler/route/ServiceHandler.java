package com.prayer.handler.route;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.constant.Constants;
import com.prayer.constant.log.DebugKey;
import com.prayer.model.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.WebSender;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Future;
import com.prayer.util.web.Interruptor;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
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
                final WebSender sender = instance(uri.getSender());
                // Fix：必须注入Response
                sender.injectResponse(context.response());
                // 检查Sender的合法性
                Interruptor.interruptClass(getClass(), uri.getSender().getName(), "Sender");
                Interruptor.interruptImplements(getClass(), uri.getSender().getName(), Handler.class);
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
