package com.prayer.util.business;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 工具类，用于解析String
 * 
 * @author Lang
 *
 */
public final class JsonTrier {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 在data中放入数据
     * 
     * @param data
     * @param key
     * @param value
     */
    public static void putTried(final JsonObject data, final String key, final String value) {
        // 1.直接解析成JsonArray
        try {
            // -> JArray
            final JsonArray array = new JsonArray(value);
            if (null != array) {
                data.put(key, array);
            }
        } catch (DecodeException ex) {
            // -> JObject
            try {
                final JsonObject object = new JsonObject(value);
                if (null != object) {
                    data.put(key, object);
                }
            } catch (DecodeException iex) {
                data.put(key, value);
            }
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonTrier() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
