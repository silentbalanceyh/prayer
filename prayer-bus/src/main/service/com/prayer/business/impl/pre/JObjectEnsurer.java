package com.prayer.business.impl.pre;

import com.prayer.fantasm.business.pre.AbstractEnsurer;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class JObjectEnsurer extends AbstractEnsurer<JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    protected JsonObject extractValue(final JsonObject data, final String attr) {
        return data.getJsonObject(attr);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
