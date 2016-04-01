package com.prayer.business.impl.pre;

import com.prayer.fantasm.business.pre.AbstractEnsurer;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class StringEnsurer extends AbstractEnsurer<String> {
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
    protected String extractValue(final JsonObject data, final String attr){
        return data.getString(attr);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
