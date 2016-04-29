package com.prayer.vertx.uca.consumer;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DataConsumer implements Handler<Message<JsonObject>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataConsumer.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final Message<JsonObject> event) {
        info(LOGGER, MessageFormat.format(MsgVertx.SEV_CONSUMER, getClass().getSimpleName(), getClass().getName()));
        /** 1.从Event Bus中读取Event **/
        final JsonObject params = event.body();
        /** 2.读取请求方法信息 **/
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        /** 3.得到ActResponse **/
        final ActResponse response = Director.Data.select(method).invoke(params);
        /** 4.构造响应数据 **/
        Envelop envelop = null;
        if (response.success()) {
            envelop = Envelop.success(response);
        } else {
            envelop = Envelop.failure(response.getError());
        }
        /** 5.返回响应结果 **/
        event.reply(envelop.result());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
