package com.prayer.vertx.uca.sender;

import com.prayer.facade.vtx.endpoint.MessageXDCR;

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
public class MessageSender implements MessageXDCR {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 响应信息 **/
    private transient HttpServerResponse response;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public MessageSender(@NotNull final HttpServerResponse response){
        this.response = response;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final AsyncResult<Message<JsonObject>> event) {
        if(event.succeeded()){
            
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
