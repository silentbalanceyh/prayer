package com.prayer.vertx.uca.sender;

import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.vtx.endpoint.MessageAsker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Resources;
import com.prayer.vertx.web.model.Envelop;

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
public class MessageSender implements MessageAsker<JsonObject> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    // ~ Instance Fields =====================================
    /** 响应信息 **/
    private transient HttpServerResponse response;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public MessageSender(@NotNull final HttpServerResponse response) {
        this.response = response;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final AsyncResult<Message<JsonObject>> event) {
        /** 1.读取编码方式 **/
        response.headers().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + Resources.ENCODING.name());
        /** 2.数据抽取 **/
        if (event.succeeded()) {
            final JsonObject data = event.result().body();
            if (null != data) {
                info(LOGGER, MessageFormat.format(MsgVertx.SEV_SENDER, getClass().getSimpleName(), getClass().getName(),
                        data.encode()));
                /** 3.操作Status **/
                final JsonObject status = this.getStatus(data);
                response.setStatusCode(status.getInteger(WebKeys.Envelop.Status.CODE));
                response.setStatusMessage(status.getString(WebKeys.Envelop.Status.MESSAGE));
                response.end(data.encode(), Resources.ENCODING.name());
            } else {
                this.fireInternalError();
            }
        } else {
            this.fireInternalError();
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void fireInternalError() {
        /** 500 Error **/
        final AbstractException error = new _500InternalServerErrorException(getClass());
        final Envelop envelop = Envelop.failure(error);
        /** 提取响应信息 **/
        response.setStatusCode(envelop.status().code());
        response.setStatusMessage(envelop.status().message());
        response.end(envelop.result().encode(), Resources.ENCODING.name());
    }

    private JsonObject getStatus(final JsonObject data) {
        return data.getJsonObject(WebKeys.Envelop.STATUS);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
