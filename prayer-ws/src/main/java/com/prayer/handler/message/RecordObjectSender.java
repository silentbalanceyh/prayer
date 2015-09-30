package com.prayer.handler.message;

import static com.prayer.assistant.WebLogger.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Future;
import com.prayer.assistant.WebLogger;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RecordObjectSender implements Handler<AsyncResult<Message<Object>>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordObjectSender.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final HttpServerResponse response;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param response
     */
    public RecordObjectSender(final HttpServerResponse response) {
        this.response = response;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final AsyncResult<Message<Object>> event) {
        if (event.succeeded()) {
            final String data = event.result().body().toString();

            final Responsor ret = responsor(data);

            Future.success(this.response, ret.getResult().encode());
        } else {
            Future.failure(this.response, event.result().body().toString(), StatusCode.INTERNAL_SERVER_ERROR.status(),
                    StatusCode.INTERNAL_SERVER_ERROR.name());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Responsor responsor(final String content) {
        // 1.Json Object
        Responsor responsor = null;
        try {
            JsonObject data = Json.decodeValue(content, JsonObject.class);
            responsor = Responsor.success(data);
        } catch (DecodeException ex) {
            error(LOGGER, WebLogger.I_COMMON_INFO, "Decoding Json Object -> " + ex.toString());
            responsor = null;
        }
        // 2.Try Json Array
        if (null == responsor) {
            try {
                JsonArray data = Json.decodeValue(content, JsonArray.class);
                responsor = Responsor.success(data);
            } catch (DecodeException ex) {
                error(LOGGER, WebLogger.I_COMMON_INFO, "Decoding Json Array -> " + ex.toString());
                responsor = null;
            }
        }
        return responsor;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
