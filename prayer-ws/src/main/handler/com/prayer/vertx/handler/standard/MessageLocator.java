package com.prayer.vertx.handler.standard;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.instance;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.uca.sender.MessageSender;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class MessageLocator implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLocator.class);
    // ~ Instance Fields =====================================

    public static MessageLocator create() {
        return new MessageLocator();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.提取参数 **/
        final JsonObject params = event.get(WebKeys.Request.Data.PARAMS);
        final PEUri uri = event.get(WebKeys.Request.Data.Meta.PEURI);
        /** 2.读取Vertx引用 **/
        final Vertx vertx = event.vertx();
        final EventBus bus = vertx.eventBus();
        /** 3.提取Sender **/
        final MessageSender sender = instance(uri.getSender(), event.response());
        /** 4.日志输出的第一个参数设置成Sender **/
        info(LOGGER, MessageFormat.format(MsgVertx.SEV_ENDDATA, sender.getClass().getSimpleName(), uri.getAddress(),
                params.encode(), sender.getClass().getName()));
        bus.send(uri.getAddress(), params, sender);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
