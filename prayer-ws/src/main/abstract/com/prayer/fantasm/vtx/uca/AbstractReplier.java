package com.prayer.fantasm.vtx.uca;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.vtx.Channel;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 默认使用Json的Replier
 * 
 * @author Lang
 *
 */
public abstract class AbstractReplier {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /**
     * 频道选择器
     **/
    public abstract Channel getChannel(final HttpMethod method) throws AbstractException;

    // ~ Override Methods ====================================
    /**
     * 日志记录器
     **/
    public final Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    // ~ Methods =============================================
    /**
     * 子类直接继承该接口方法
     * 
     * @param event
     */
    public void handle(@NotNull final Message<JsonObject> event) {
        info(getLogger(),
                MessageFormat.format(MsgVertx.SEV_CONSUMER, getClass().getSimpleName(), getClass().getName()));
        /** 1.从Event Bus中读取Event **/
        final JsonObject params = event.body();
        /** 2.读取请求方法信息 **/
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        Envelop envelop = null;
        try {
            /** 3.得到ActResponse **/
            final ActResponse response = this.getChannel(method).invoke(params);
            /** 4.构造响应数据 **/
            if (response.success()) {
                envelop = Envelop.success(response);
            } else {
                envelop = Envelop.failure(response.getError());
            }
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            envelop = Envelop.failure(ex);
        }
        /** 5.返回响应结果 **/
        event.reply(envelop.result());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
