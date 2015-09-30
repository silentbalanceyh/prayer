package com.prayer.handler.message;

import com.prayer.assistant.Future;
import com.prayer.constant.Constants;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
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
        if (StringUtil.equals(Constants.EMPTY_JARR, content)) {
            responsor = Responsor.success(new JsonArray());
        } else if (StringUtil.equals(Constants.EMPTY_JOBJ, content)) {
            responsor = Responsor.success(new JsonObject());
        } else if (StringUtil.startsWithChar(content,'{')){
            JsonObject data = new JsonObject(content);
            responsor = Responsor.success(data);
        }else if(StringUtil.startsWithChar(content, '[')){
            JsonArray data = new JsonArray(content);
            responsor = Responsor.success(data);
        }
        return responsor;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
