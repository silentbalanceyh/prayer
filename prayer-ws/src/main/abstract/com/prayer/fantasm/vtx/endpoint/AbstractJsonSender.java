package com.prayer.fantasm.vtx.endpoint;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.vtx.endpoint.MessageAsker;
import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Resources;
import com.prayer.vertx.uca.responder.FailureResponder;
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
public abstract class AbstractJsonSender implements MessageAsker<JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 响应信息 **/
    private transient HttpServerResponse response;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param response
     */
    public AbstractJsonSender(@NotNull final HttpServerResponse response){
        this.response = response;
    }
    // ~ Abstract Methods ====================================
    /**
     * 构建响应器
     */
    public abstract Responder<JsonObject> getResponder();
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final AsyncResult<Message<JsonObject>> event) {
        /** 1.判断分流 **/
        if (event.succeeded()) {
            final JsonObject data = event.result().body();
            if (null != data) {
                info(getLogger(), MessageFormat.format(MsgVertx.SEV_SENDER, getClass().getSimpleName(), getClass().getName(),
                        data.toString()));
                /** 3.操作Status **/
                getResponder().reckonHeaders(this.response, data);
                /** 4.生成响应 **/
                response.end(getResponder().buildBody(data), Resources.ENCODING.name());
            } else {
                this.fireFailure();
            }
        } else {
            this.fireFailure();
        }
    }
    // ~ Methods =============================================
    /**
     * 日志器
     * @return
     */
    public final Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }
    // ~ Private Methods =====================================

    private void fireFailure() {
        /** 1.500 Error **/
        final AbstractException error = new _500InternalServerErrorException(getClass());
        final Envelop envelop = Envelop.failure(error);
        /** 2.构造响应器 **/
        final Responder<Envelop> responder = singleton(FailureResponder.class);
        responder.reckonHeaders(this.response, envelop);
        /** 3.生成最终响应结果 **/
        response.end(responder.buildBody(envelop), Resources.ENCODING.name());
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
