package com.prayer.vertx.uca.sender;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.vtx.uca.request.Responder;
import com.prayer.fantasm.vtx.endpoint.AbstractJsonSender;
import com.prayer.vertx.uca.responder.JObjectResponder;

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
public class JObjectSender extends AbstractJsonSender {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public JObjectSender(@NotNull final HttpServerResponse response) {
        super(response);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 读取对应的Responder **/
    @Override
    public Responder<JsonObject> getResponder() {
        return singleton(JObjectResponder.class);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
