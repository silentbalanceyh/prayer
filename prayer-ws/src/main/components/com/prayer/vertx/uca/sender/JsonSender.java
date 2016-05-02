package com.prayer.vertx.uca.sender;

import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.vtx.endpoint.MessageAsker;
import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.resource.Resources;

import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JsonSender implements MessageAsker<JsonObject> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSender.class);
    // ~ Instance Fields =====================================
    /** 响应信息 **/
    private transient final HttpServerResponse response;
    /** 读取Responder **/
    private transient final Responder<JsonObject> responder;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public JsonSender(@NotNull final HttpServerResponse response, @NotNull final Responder<JsonObject> responder) {
        this.response = response;
        this.responder = responder;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** **/
    @Override
    public void handle(final AsyncResult<Message<JsonObject>> event) {
        /** 1.判断分流 **/
        if (event.succeeded()) {
            final JsonObject data = event.result().body();
            if (null != data) {
                info(LOGGER, MessageFormat.format(MsgVertx.SEV_SENDER, getClass().getSimpleName(), getClass().getName(),
                        data.toString()));
                /** 3.操作Status **/
                this.responder.reckonHeaders(this.response, data);
                /** 4.生成响应 **/
                response.end(this.responder.buildBody(data), Resources.ENCODING.name());
            } else {
                ErrorAtaghan.send500Error(getClass(), this.response);
            }
        } else {
            ErrorAtaghan.send500Error(getClass(), this.response);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
