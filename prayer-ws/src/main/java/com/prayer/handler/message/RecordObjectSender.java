package com.prayer.handler.message;

import com.prayer.assistant.Future;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.web.StatusCode;

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
public final class RecordObjectSender implements Handler<AsyncResult<Message<Object>>> {
    // ~ Static Fields =======================================
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
            final String data = (String) event.result().body();

            final JsonObject ret = new JsonObject();
            ret.put(Constants.RET.STATUS_CODE, StatusCode.OK.status());
            ret.put(Constants.RET.RESPONSE, ResponseCode.SUCCESS);
            ret.put(Constants.RET.DATA, data);
            
            Future.success(this.response, ret.encode());
        }else{
            System.out.println("Hello");
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
