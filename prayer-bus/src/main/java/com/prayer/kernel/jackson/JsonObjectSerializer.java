package com.prayer.kernel.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class JsonObjectSerializer extends JsonSerializer<JsonObject> {    // NOPMD

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void serialize(final JsonObject jsonObject, final JsonGenerator jsonGen, final SerializerProvider provider)
            throws IOException, JsonProcessingException {
        final String value = jsonObject.encodePrettily();
        jsonGen.writeString(value);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
