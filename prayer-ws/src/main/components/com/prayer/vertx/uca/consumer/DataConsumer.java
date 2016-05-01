package com.prayer.vertx.uca.consumer;

import com.prayer.facade.vtx.endpoint.Channel;
import com.prayer.facade.vtx.endpoint.MessageReplier;
import com.prayer.fantasm.vtx.endpoint.AbstractReplier;

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
public class DataConsumer extends AbstractReplier implements MessageReplier<JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Channel getChannel(@NotNull final HttpMethod method){
        return Director.Data.select(method);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
