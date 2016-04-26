package com.prayer.uca.sender;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.constant.log.DebugKey;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.StatusCode;
import com.prayer.uca.WebSender;
import com.prayer.util.web.Future;

import io.vertx.core.AsyncResult;
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
public final class JsonRecordSender implements WebSender {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRecordSender.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient HttpServerResponse response;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param response
     */
    @Override
    public void injectResponse(final HttpServerResponse response) {
        this.response = response;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final AsyncResult<Message<Object>> event) {
        debug(LOGGER, DebugKey.WEB_UCA, "Sender --> " + getClass().toString());
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
                Future.failure(this.response, "event.result() == null", StatusCode.INTERNAL_SERVER_ERROR.code(),
                        StatusCode.INTERNAL_SERVER_ERROR.name());
            } else {
                Future.failure(this.response, event.result().body().toString(),
                        StatusCode.INTERNAL_SERVER_ERROR.code(), StatusCode.INTERNAL_SERVER_ERROR.name());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
