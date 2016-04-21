package com.prayer.console.thirdpart.hc;

import com.prayer.facade.console.Connector;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastConnector implements Connector{
    /** **/
    @Override
    public boolean connecting(final String[] args) {
        // TODO 将参数转换并且读取配置
        return true;
    }

    @Override
    public JsonObject read() {
        // TODO 读取配置
        return new JsonObject();
    }
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
