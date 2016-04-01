package com.prayer.business.impl.pre;

import com.prayer.fantasm.business.pre.AbstractEnsurer;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class IntegerEnsurer extends AbstractEnsurer<Integer>{
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
    protected Integer extractValue(final JsonObject data, final String attr){
        return data.getInteger(attr);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
