package com.prayer.vertx.uca.consumer;

import com.prayer.exception.web._500MethodNotSupportException;
import com.prayer.facade.vtx.Channel;
import com.prayer.facade.vtx.endpoint.MessageReplier;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.vtx.uca.AbstractReplier;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 使用POST请求发送GET的Service接口调用
 * 
 * @author Lang
 *
 */
@Guarded
public class CrossConsumer extends AbstractReplier implements MessageReplier<JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Channel getChannel(@NotNull final HttpMethod method) throws AbstractException {
        Channel channel = null;
        if (HttpMethod.POST == method) {
            channel = Director.Data.select(HttpMethod.GET);
        } else {
            throw new _500MethodNotSupportException(getClass(), method);
        }
        return channel;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
