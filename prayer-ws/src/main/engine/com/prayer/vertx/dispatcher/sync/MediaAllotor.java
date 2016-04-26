package com.prayer.vertx.dispatcher.sync;

import com.prayer.facade.vtx.request.Allotor;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 同步模式即可，位于Normalize之后，可传入params
 * 
 * @author Lang
 *
 */
public class MediaAllotor implements Allotor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop accept(final RoutingContext context, final JsonObject params) {
        /** 1.使用上一步检测的结果读取PEUri **/
        
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private PEUri buildUri(final HttpMethod method, final JsonObject params){
        final JsonObject data = params.getJsonObject(method.name());
        return new PEUri(data);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
