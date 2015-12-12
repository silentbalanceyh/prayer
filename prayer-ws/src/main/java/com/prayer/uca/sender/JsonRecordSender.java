package com.prayer.uca.sender;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Converter.fromStr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Future;
import com.prayer.assistant.WebLogger;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
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
public final class JsonRecordSender implements Handler<AsyncResult<Message<Object>>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRecordSender.class);
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
    public JsonRecordSender(final HttpServerResponse response) {
        this.response = response;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final AsyncResult<Message<Object>> event) {
        info(LOGGER, WebLogger.I_COMMON_INFO, "Sender --> " + getClass().toString());
        if (event.succeeded()) {
            final JsonObject retData = (JsonObject) event.result().body();

            final ResponseCode code = fromStr(ResponseCode.class, retData.getString(JsonKey.RESPONSOR.RETURNCODE));
            // Return Service Error ?
            if (ResponseCode.SUCCESS == code) {
                Future.success(this.response, retData.encode());
            } else {
                final JsonObject status = retData.getJsonObject(JsonKey.RESPONSOR.STATUS.NAME);
                Future.failure(response, retData.encode(), status.getInteger(JsonKey.RESPONSOR.STATUS.CODE),
                        status.getString(JsonKey.RESPONSOR.STATUS.LITERAL));
            }
        } else {
            if (null == event.result()) {
                Future.failure(this.response, "event.result() == null", StatusCode.INTERNAL_SERVER_ERROR.status(),
                        StatusCode.INTERNAL_SERVER_ERROR.name());
            } else {
                Future.failure(this.response, event.result().body().toString(),
                        StatusCode.INTERNAL_SERVER_ERROR.status(), StatusCode.INTERNAL_SERVER_ERROR.name());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
